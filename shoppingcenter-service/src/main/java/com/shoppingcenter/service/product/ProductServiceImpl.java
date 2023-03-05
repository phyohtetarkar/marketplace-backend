package com.shoppingcenter.service.product;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.discount.DiscountRepo;
import com.shoppingcenter.data.product.FavoriteProductRepo;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductImageEntity;
import com.shoppingcenter.data.product.ProductImageRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.product.variant.ProductVariantEntity;
import com.shoppingcenter.data.product.variant.ProductVariantRepo;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.shoppingcart.CartItemRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.authorization.AuthenticationContext;
import com.shoppingcenter.service.product.model.Product;
import com.shoppingcenter.service.product.model.ProductImage;
import com.shoppingcenter.service.product.model.ProductOption;
import com.shoppingcenter.service.product.model.ProductVariant;
import com.shoppingcenter.service.shop.ShopMemberService;
import com.shoppingcenter.service.shop.ShopService;
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
    private ProductVariantRepo productVariantRepo;

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private FavoriteProductRepo favoriteProductRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ShopMemberService shopMemberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private PolicyFactory policyFactory;

    @Value("${app.image.base-path}")
    private String imagePath;

    @Override
    public void save(Product product) {

        ProductEntity entity = productRepo.findById(product.getId()).orElseGet(ProductEntity::new);

        if (!StringUtils.hasText(product.getName())) {
            throw new ApplicationException("Required product name");
        }

        if (!StringUtils.hasText(product.getSlug())) {
            throw new ApplicationException("Required product slug");
        }

        if (entity.getId() == 0 || !Utils.equalsIgnoreCase(entity.getName(), product.getName())) {
            String prefix = product.getSlug().replaceAll("\\s+", "-").toLowerCase();
            String slug = Utils.generateSlug(prefix, productRepo::existsBySlug);
            entity.setSlug(slug);
        }

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
                throw new ApplicationException("Shop not found");
            }
            ShopEntity shop = shopRepo.getReferenceById(product.getShopId());

            entity.setShop(shop);
        }

        shopService.validateActive(entity.getShop());

        if (!categoryRepo.existsById(product.getCategoryId())) {
            throw new ApplicationException("Category not found");
        }

        CategoryEntity category = categoryRepo.getReferenceById(product.getCategoryId());

        entity.setCategory(category);

        if (product.getDiscountId() != null && discountRepo.existsById(product.getDiscountId())) {
            entity.setDiscount(discountRepo.getReferenceById(product.getDiscountId()));
        } else {
            entity.setDiscount(null);
        }

        List<ProductVariant> variants = Optional.ofNullable(product.getVariants()).orElseGet(ArrayList::new);

        if (variants.size() > 0) {
            entity.setWithVariant(true);
            entity.setPrice(variants.stream().mapToDouble(ProductVariant::getPrice).min().orElse(0));
        }

        if (!entity.isWithVariant() && entity.getPrice() == null) {
            throw new ApplicationException("Requried product price");
        }

        ProductEntity result = productRepo.save(entity);

        List<ProductImage> images = Optional.ofNullable(product.getImages()).orElseGet(ArrayList::new);

        List<String> deletedImages = new ArrayList<>();
        Map<String, UploadFile> uploadedImages = new HashMap<>();

        boolean atLeastOneImage = images.stream().anyMatch(img -> img.isDeleted() == false);

        if (!atLeastOneImage) {
            throw new ApplicationException("At least one image required");
        }

        for (ProductImage image : images) {
            long imageId = image.getId();
            if (image.isDeleted()) {
                deletedImages.add(image.getName());
                productImageRepo.deleteById(image.getId());
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

            // if (image.isThumbnail()) {
            // result.setThumbnail(imageEntity.getName());
            // }
        }

        // if (!StringUtils.hasText(result.getThumbnail())) {
        // uploadedImages.keySet().stream().findFirst().ifPresent(result::setThumbnail);
        // }

        List<ProductOption> options = Optional.ofNullable(product.getOptions()).orElseGet(ArrayList::new);

        if (isNewProduct) {
            // for (ProductOption option : options) {
            // ProductOptionEntity optionEntity = new ProductOptionEntity();
            // optionEntity.setId(option.getId());
            // optionEntity.setProduct(result);
            // optionEntity.setName(option.getName());
            // optionEntity.setPosition(option.getPosition());

            // productOptionRepo.save(optionEntity);
            // }
        }

        boolean atLeastOneVariant = variants.stream().anyMatch(pv -> pv.isDeleted() == false);

        if (result.isWithVariant() && !atLeastOneVariant) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "At least one variant required");
        }

        for (ProductVariant variant : variants) {
            if (variant.isDeleted()) {
                productVariantRepo.deleteById(variant.getId());
                continue;
            }

            if (variant.getOptions() == null || variant.getOptions().isEmpty()) {
                throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Invalid variant options");
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

            // try {
            // variantEntity.setOptions(objectMapper.writeValueAsString(variant.getOptions()));
            // } catch (JsonProcessingException e) {
            // throw new ApplicationException(e.getMessage());
            // }

            productVariantRepo.save(variantEntity);
        }

        String dir = imagePath + File.separator + "product" + File.separator + result.getShop().getId();
        storageService.write(uploadedImages.entrySet(), dir);

        if (deletedImages.size() > 0) {
            storageService.delete(dir, deletedImages);
        }

    }

    @Override
    public void delete(long id) {

        if (!productRepo.existsById(id)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Product not found");
        }

        ProductEntity entity = productRepo.getReferenceById(id);

        var shopId = entity.getShop().getId();

        shopMemberService.validateMember(shopId, authenticationContext.getUserId());

        var images = entity.getImages().stream().map(ProductImageEntity::getName).collect(Collectors.toList());
        String dir = imagePath + File.separator + "product" + File.separator + shopId;

        // remove cartItems
        cartItemRepo.deleteByProduct_Id(id);

        // remove favorites
        favoriteProductRepo.deleteByProduct_Id(id);

        // TODO: remove variants

        // TODO: remove options

        // TODO: remove images

        // delete product
        productRepo.deleteById(id);

        // delete images form storage
        storageService.delete(dir, images);
    }

}
