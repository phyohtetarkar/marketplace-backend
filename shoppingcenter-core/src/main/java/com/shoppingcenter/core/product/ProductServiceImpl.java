package com.shoppingcenter.core.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.core.product.model.ProductImage;
import com.shoppingcenter.core.product.model.ProductOption;
import com.shoppingcenter.core.product.model.ProductVariant;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductImageEntity;
import com.shoppingcenter.data.product.ProductImageRepo;
import com.shoppingcenter.data.product.ProductOptionEntity;
import com.shoppingcenter.data.product.ProductOptionRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.variant.ProductVariantEntity;
import com.shoppingcenter.data.variant.ProductVariantRepo;

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

    @Override
    public void save(Product product) {

        // TODO: check privilege

        ProductEntity entity = productRepo.findById(product.getId()).orElseGet(ProductEntity::new);
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setSlug(product.getSlug());
        entity.setBrand(product.getBrand());
        entity.setPrice(product.getPrice());
        entity.setOutOfStock(product.isOutOfStock());
        entity.setDescription(product.getDescription());

        if (entity.getId() <= 0) {
            if (!shopRepo.existsById(product.getShopId())) {
                throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
            }
            ShopEntity shop = shopRepo.getReferenceById(product.getShopId());
            entity.setShop(shop);
        }

        if (!categoryRepo.existsById(product.getCategoryId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        CategoryEntity category = categoryRepo.getReferenceById(product.getCategoryId());

        entity.setCategory(category);

        ProductEntity result = productRepo.save(entity);

        List<ProductImage> images = Optional.ofNullable(product.getImages()).orElseGet(ArrayList::new);

        List<String> deletedImages = new ArrayList<>();

        for (ProductImage image : images) {
            ProductImageEntity.ID imageId = new ProductImageEntity.ID();
            imageId.setProductId(result.getId());
            imageId.setCreatedAt(image.getCreatedAt());
            if (image.isDeleted()) {
                productImageRepo.deleteById(imageId);
                deletedImages.add(image.getName());
                continue;
            }

            ProductImageEntity imageEntity = productImageRepo.findById(imageId).orElseGet(ProductImageEntity::new);
            imageEntity.setProductId(result.getId());
            imageEntity.setThumbnail(image.isThumbnail());

            if (image.getFile() != null && image.getFile().getSize() > 0) {
                imageEntity.setSize(image.getFile().getSize());
                imageEntity.setName(String.format("%d-%d.%s", result.getId(), System.currentTimeMillis(),
                        image.getFile().getExtension()));
            }

            productImageRepo.save(imageEntity);

            if (image.isThumbnail()) {
                result.setThumbnail(imageEntity.getName());
            }

        }

        List<ProductOption> options = Optional.ofNullable(product.getOptions()).orElseGet(ArrayList::new);

        if (product.getId() <= 0) {
            for (ProductOption option : options) {
                ProductOptionEntity optionEntity = new ProductOptionEntity();
                optionEntity.setProductId(result.getId());
                optionEntity.setName(option.getName());
                optionEntity.setPosition(option.getPosition());

                productOptionRepo.save(optionEntity);
            }
        }

        List<ProductVariant> variants = Optional.ofNullable(product.getVariants()).orElseGet(ArrayList::new);

        for (ProductVariant variant : variants) {
            ProductVariantEntity.ID variantId = new ProductVariantEntity.ID();
            variantId.setProductId(result.getId());
            variantId.setOptionPath(variant.getOptionPath());
            if (variant.isDeleted()) {
                productVariantRepo.deleteById(variantId);
                continue;
            }
            ProductVariantEntity variantEntity = new ProductVariantEntity();
            variantEntity.setProductId(result.getId());
            variantEntity.setTitle(variant.getTitle());
            variantEntity.setPrice(variant.getPrice());
            variantEntity.setSku(variant.getSku());
            variantEntity.setOutOfStock(variant.isOutOfStock());
            if (variant.getOptions() == null || variant.getOptions().isEmpty()) {
                throw new ApplicationException();
            }

            variantEntity.setOptionPath(variant.getOptionPath());
            variantEntity.setOptions(variant.getOptions().stream().map(op -> {
                return String.format("{option:\"%s\", value:\"%s\"}", op.getOption(), op.getValue());
            }).collect(Collectors.joining(",", "[", "]")));

            productVariantRepo.save(variantEntity);
        }

        // TODO: delete images from storage

        // TODO: upload images to storage

    }

    @Override
    public void delete(long id) {
        // TODO: check privilege

        // TODO: remove cartItems

        // TODO: remove favorites

        // TODO: remove variants

        // TODO: remove options

        // TODO: remove images

        // TODO: delete product

        // TODO: delete images form storage
    }

}
