package com.shoppingcenter.service.product;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.discount.DiscountRepo;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductImageEntity;
import com.shoppingcenter.data.product.ProductImageRepo;
import com.shoppingcenter.data.product.ProductOptionEntity;
import com.shoppingcenter.data.product.ProductOptionRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.variant.ProductVariantEntity;
import com.shoppingcenter.data.variant.ProductVariantRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.authorization.IAuthenticationFacade;
import com.shoppingcenter.service.product.model.Product;
import com.shoppingcenter.service.product.model.ProductImage;
import com.shoppingcenter.service.product.model.ProductOption;
import com.shoppingcenter.service.product.model.ProductVariant;
import com.shoppingcenter.service.storage.FileStorageService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductImageRepo productImageRepo;

    @Autowired
    private ProductOptionRepo productOptionRepo;

    @Autowired
    private ProductVariantRepo productVariantRepo;

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private PolicyFactory policyFactory;

    @Value("${app.image.base-path}")
    private String imagePath;

    @Override
    public void save(Product product) {

        // TODO: check privilege

        ProductEntity entity = productRepo.findById(product.getId()).orElseGet(ProductEntity::new);
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setBrand(product.getBrand());
        entity.setPrice(product.getPrice());
        entity.setStockLeft(product.getStockLeft());
        entity.setFeatured(product.isFeatured());
        entity.setNewArrival(product.isNewArrival());
        entity.setStatus(product.getStatus().name());

        // Sanitize rich text for XSS attack
        if (StringUtils.hasText(product.getDescription())) {
            entity.setDescription(policyFactory.sanitize(product.getDescription()));
        }

        boolean isNewProduct = entity.getId() <= 0;

        if (isNewProduct) {
            if (!shopRepo.existsById(product.getShopId())) {
                throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Shop not found");
            }
            ShopEntity shop = shopRepo.getReferenceById(product.getShopId());
            entity.setShop(shop);

            String slug = generateSlug(product.getName().replaceAll("\\s+", "-").toLowerCase());
            entity.setSlug(slug);
        }

        if (!categoryRepo.existsById(product.getCategoryId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Category not found");
        }

        CategoryEntity category = categoryRepo.getReferenceById(product.getCategoryId());

        entity.setCategory(category);

        if (product.getDiscountId() != null && discountRepo.existsById(product.getDiscountId())) {
            entity.setDiscount(discountRepo.getReferenceById(product.getDiscountId()));
        } else {
            entity.setDiscount(null);
        }

        ProductEntity result = productRepo.save(entity);

        List<ProductImage> images = Optional.ofNullable(product.getImages()).orElseGet(ArrayList::new);

        List<String> deletedImages = new ArrayList<>();
        Map<String, UploadFile> uploadedImages = new HashMap<>();

        for (ProductImage image : images) {
            long imageId = image.getId();
            if (image.isDeleted()) {
                productImageRepo.deleteById(image.getId());
                deletedImages.add(image.getName());
                continue;
            }

            ProductImageEntity imageEntity = productImageRepo.findById(imageId).orElseGet(ProductImageEntity::new);
            imageEntity.setProduct(result);
            imageEntity.setThumbnail(image.isThumbnail());

            if (imageEntity.getId() <= 0 && (image.getFile() == null || image.getFile().getSize() <= 0)) {
                throw new ApplicationException("Uploaded image file must not empty");
            }

            if (image.getFile() != null && image.getFile().getSize() > 0) {
                // long millis = System.currentTimeMillis();
                imageEntity.setSize(image.getFile().getSize());

                ProductImageEntity imageResult = productImageRepo.save(imageEntity);

                String imageName = String.format("product-%d-%d.%s", result.getId(), imageResult.getId(),
                        image.getFile().getExtension());

                imageResult.setName(imageName);

                uploadedImages.put(imageName, image.getFile());
            }

            if (image.isThumbnail()) {
                result.setThumbnail(imageEntity.getName());
            }
        }

        List<ProductOption> options = Optional.ofNullable(product.getOptions()).orElseGet(ArrayList::new);

        if (isNewProduct) {
            for (ProductOption option : options) {
                ProductOptionEntity optionEntity = new ProductOptionEntity();
                optionEntity.setId(option.getId());
                optionEntity.setProduct(result);
                optionEntity.setName(option.getName());
                optionEntity.setPosition(option.getPosition());

                productOptionRepo.save(optionEntity);
            }
        }

        List<ProductVariant> variants = Optional.ofNullable(product.getVariants()).orElseGet(ArrayList::new);

        if (variants.size() > 0) {
            entity.setPrice(variants.stream().mapToDouble(ProductVariant::getPrice).min().orElse(0));
        }

        for (ProductVariant variant : variants) {
            if (variant.isDeleted()) {
                productVariantRepo.deleteById(variant.getId());
                continue;
            }

            if (variant.getOptions() == null || variant.getOptions().isEmpty()) {
                throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Invalid variant options");
            }

            ProductVariantEntity variantEntity = new ProductVariantEntity();
            variantEntity.setId(variant.getId());
            variantEntity.setProduct(result);
            variantEntity.setTitle(variant.getTitle());
            variantEntity.setPrice(variant.getPrice());
            variantEntity.setSku(variant.getSku());
            variantEntity.setStockLeft(variant.getStockLeft());

            // variantEntity.setOptions(variant.getOptions().stream().map(op -> {
            // return String.format("{option:\"%s\", value:\"%s\"}", op.getOption(),
            // op.getValue());
            // }).collect(Collectors.joining(",", "[", "]")));

            try {
                variantEntity.setOptions(objectMapper.writeValueAsString(variant.getOptions()));
            } catch (JsonProcessingException e) {
                throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, e.getMessage());
            }

            productVariantRepo.save(variantEntity);
        }

        String dir = imagePath + File.separator + "product" + File.separator + result.getShop().getId();
        try {
            storageService.write(uploadedImages.entrySet(), dir);
        } catch (Exception e) {
            throw new ApplicationException("Failed to upload product images");
        }

        try {
            if (deletedImages.size() > 0) {
                storageService.delete(dir, deletedImages);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void delete(long id) {

        if (!productRepo.existsById(id)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Product not found");
        }

        ProductEntity entity = productRepo.getReferenceById(id);

        if (!shopMemberRepo.existsByShop_IdAndUser_Id(entity.getShop().getId(), authenticationFacade.getUserId())) {
            throw new AccessDeniedException("Permission denied");
        }

        // TODO: remove cartItems

        // TODO: remove favorites

        // TODO: remove variants

        // TODO: remove options

        // TODO: remove images

        // TODO: delete product

        // TODO: delete images form storage
    }

    private String generateSlug(String prefix) {
        String result = prefix + "-" + Utils.generateRandomCode(5);

        return productRepo.existsBySlug(result) ? generateSlug(prefix) : result;
    }

}
