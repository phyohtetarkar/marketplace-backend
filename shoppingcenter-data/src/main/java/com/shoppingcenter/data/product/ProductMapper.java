package com.shoppingcenter.data.product;

import java.util.stream.Collectors;

import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.discount.DiscountMapper;
import com.shoppingcenter.data.product.variant.ProductVariantEntity;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.ProductOption;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.ProductVariantOption;


public class ProductMapper {

//    private static String imageBaseUrl(String baseUrl) {
//        if (baseUrl != null) {
//            return String.format("%s%s/", baseUrl, "product");
//        }
//
//        return "";
//    }

    public static Product toDomain(ProductEntity entity) {
        var p = toDomainCompat(entity);
        p.setDescription(entity.getDescription());
        p.setCategory(CategoryMapper.toDomain(entity.getCategory()));
        if (entity.getOptions() != null) {
            p.setOptions(entity.getOptions().stream().map(ProductMapper::toOption).collect(Collectors.toList()));
        }
        var images = entity.getImages();
        if (images != null) {
            p.setImages(images.stream().map(e -> toImage(e, p)).toList());
        }
        return p;
    }

    public static Product toDomainCompat(ProductEntity entity) {
        var p = new Product();
        p.setId(entity.getId());
        p.setName(entity.getName());
        p.setSlug(entity.getSlug());
        p.setBrand(entity.getBrand());
        p.setPrice(entity.getPrice());
        p.setStockLeft(entity.getStockLeft());
        p.setFeatured(entity.isFeatured());
        p.setHidden(entity.isHidden());
        p.setDisabled(entity.isDisabled());
        p.setNewArrival(entity.isNewArrival());
        p.setCategory(CategoryMapper.toDomainCompat(entity.getCategory()));
        p.setShop(ShopMapper.toDomainCompat(entity.getShop()));
        p.setCreatedAt(entity.getCreatedAt());
        p.setWithVariant(entity.isWithVariant());
        p.setThumbnail(entity.getThumbnail());
        p.setVideoUrl(entity.getVideoUrl());
        if (entity.getDiscount() != null) {
            p.setDiscount(DiscountMapper.toDomain(entity.getDiscount()));
        }
        return p;
    }

    public static ProductImage toImage(ProductImageEntity entity, Product product) {
        var image = new ProductImage();
        image.setId(entity.getId());
        image.setThumbnail(entity.isThumbnail());
        image.setSize(entity.getSize());
        image.setName(entity.getName());
        image.setShopId(product.getShop().getId());
        return image;
    }

    public static ProductOption toOption(ProductOptionEntity entity) {
        var op = new ProductOption();
        op.setName(entity.getName());
        op.setPosition(entity.getPosition());
        return op;
    }

    public static ProductVariant toVariant(ProductVariantEntity entity) {
        var pv = new ProductVariant();
        pv.setId(entity.getId());
        pv.setTitle(entity.getTitle());
        pv.setSku(entity.getSku());
        pv.setPrice(entity.getPrice());
        pv.setStockLeft(entity.getStockLeft());
        pv.setOptions(entity.getOptions().stream().map(op -> {
            var variantOption = new ProductVariantOption();
            variantOption.setOption(op.getOption());
            variantOption.setValue(op.getValue());
            return variantOption;
        }).toList());
        return pv;
    }

}
