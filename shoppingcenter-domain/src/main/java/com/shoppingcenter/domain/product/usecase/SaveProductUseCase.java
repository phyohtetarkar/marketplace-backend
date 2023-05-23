package com.shoppingcenter.domain.product.usecase;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductEditInput;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductImageDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class SaveProductUseCase {

    private ProductDao productDao;

    private ProductImageDao imageDao;

    private ProductVariantDao variantDao;

    private ShopDao shopDao;
    
    private CategoryDao categoryDao;
    
    private FileStorageAdapter fileStorageAdapter;

    private HTMLStringSanitizer htmlStringSanitizer;

    public void apply(ProductEditInput data) {

        if (!Utils.hasText(data.getName())) {
            throw new ApplicationException("Required product name");
        }

        if (!Utils.hasText(data.getSlug())) {
            throw new ApplicationException("Required product slug");
        }

        if (!categoryDao.existsById(data.getCategoryId())) {
            throw new ApplicationException("Required category");
        }
        
        if (!shopDao.existsById(data.getShopId())) {
            throw new ApplicationException("Required shop");
        }
        
        if (!shopDao.existsByIdAndExpiredAtGreaterThan(data.getShopId(), System.currentTimeMillis())) {
        	throw new ApplicationException("Shop needs subscription");
        }

        if (Utils.hasText(data.getDescription())) {
            var desc = data.getDescription();
            data.setDescription(htmlStringSanitizer.sanitize(desc));
        }
        
        var slug = Utils.convertToSlug(data.getName());
        
        if (!Utils.hasText(slug)) {
        	throw new ApplicationException("Invalid slug value");
        }
        
        data.setSlug(slug);
        
        var images = Optional.ofNullable(data.getImages()).orElseGet(ArrayList::new);

        boolean atLeastOneImage = images.stream().anyMatch(img -> img.isDeleted() == false);

        if (!atLeastOneImage) {
            throw new ApplicationException("At least one image required");
        }

        var variants = Optional.ofNullable(data.getVariants()).orElseGet(ArrayList::new);
        
        if (data.isWithVariant() && (data.getAttributes() == null || data.getAttributes().isEmpty())) {
        	throw new ApplicationException("Required product attributes");
        }
        
        if (data.isWithVariant() && variants.isEmpty()) {
        	throw new ApplicationException("Required product variants");
        }
        
        if (data.isWithVariant()) {
        	data.setPrice(variants.stream().map(ProductVariant::getPrice).sorted((f, s) -> f.compareTo(s)).findFirst().orElse(null));
        	data.setStockLeft(variants.stream().filter(v -> !v.isDeleted()).mapToInt(v -> v.getStockLeft()).sum());
        }
        
        if (data.getPrice() == null) {
        	throw new ApplicationException("Required product price");
        }
        
        if (data.getStatus() == null) {
        	data.setStatus(Product.Status.DRAFT);
        }

        var productId = productDao.save(data);
        
        var deletedImages = new ArrayList<String>();
        var uploadedImages = new HashMap<String, UploadFile>();

        var deletedImageList = new ArrayList<Long>();
        var uploadedImageList = new ArrayList<ProductImage>();

        var thumbnail = data.getThumbnail();

        var instant = Instant.now();
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        for (var image : images) {
            if (image.isDeleted()) {
                deletedImages.add(image.getName());
                deletedImageList.add(image.getId());
                // imageDao.delete(image.getId());
                continue;
            }

            if (image.getId() <= 0 && (image.getFile() == null || image.getFile().getSize() <= 0)) {
                throw new ApplicationException("Uploaded image file must not empty");
            }

            if (image.getFile() != null && image.getFile().getSize() > 0) {
            	
            	var fileSize = image.getFile().getSize() / (1024.0 * 1024.0);
        		
        		if (fileSize > 0.512) {
        			throw new ApplicationException("File size must not greater than 512KB");
        		}
        		
                image.setSize(image.getFile().getSize());
                var suffix = image.getFile().getExtension();
                var dateTime = dateTimeFormatter.format(instant);
                String imageName = String.format("%d-%s-%d.%s", data.getShopId(), slug, dateTime, suffix);

                image.setName(imageName);

                uploadedImages.put(imageName, image.getFile());
            }

            if (image.isThumbnail()) {
                thumbnail = image.getName();
            }

            image.setProductId(productId);

            uploadedImageList.add(image);
            
            instant.plus(1, ChronoUnit.SECONDS);
        }

        imageDao.deleteAll(deletedImageList);
        imageDao.saveAll(uploadedImageList);

        if (thumbnail == null) {
            var imageName = uploadedImages.keySet().stream().findFirst().orElseGet(() -> {
                return data.getImages().get(0).getName();
            });
            productDao.updateThumbnail(productId, imageName);
        } else if (data.getThumbnail() == null || !thumbnail.equals(data.getThumbnail())) {
            productDao.updateThumbnail(productId, thumbnail);
        }

        var deletedVariantList = new ArrayList<Long>();
        var variantList = new ArrayList<ProductVariant>();
        
        var attributes = productDao.getProductAttributes(productId);

        for (var variant : variants) {
            if (variant.isDeleted()) {
                deletedVariantList.add(variant.getId());
                continue;
            }
            
            var variantAttributes = variant.getAttributes();

            if (variantAttributes == null || variantAttributes.isEmpty()) {
                throw new ApplicationException("Invalid variant attributes");
            }
            
            variant.setProductId(productId);
            
            if (variant.getId() <= 0) {
            	for (var va : variantAttributes) {
                	var attribute = attributes.stream().filter(a -> a.getName().equalsIgnoreCase(va.getAttribute())).findFirst();
                	
                	if (attribute.isEmpty()) {
                		throw new ApplicationException("Invalid variant attribute");
                	}
                	
                	va.setAttributeId(attribute.get().getId());
                }
            }

            variantList.add(variant);
        }

        variantDao.deleteAll(deletedVariantList);
        variantDao.saveAll(variantList);

        var dir = Constants.IMG_PRODUCT_ROOT;

        fileStorageAdapter.write(uploadedImages.entrySet(), dir);

        if (deletedImages.size() > 0) {
            fileStorageAdapter.delete(dir, deletedImages);
        }
    }

}
