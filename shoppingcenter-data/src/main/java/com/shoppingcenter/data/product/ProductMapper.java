package com.shoppingcenter.data.product;

import java.util.stream.Collectors;

import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.discount.DiscountMapper;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductAttribute;
import com.shoppingcenter.domain.product.ProductAttributeValue;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.ProductVariantAttribute;


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
        if (entity.getAttributes() != null) {
        	var attributes = entity.getAttributes().stream().map(e -> {
        		var a = new ProductAttribute();
        		a.setId(e.getId());
        		a.setName(e.getName());
        		a.setSort(e.getSort());
        		a.setValues(e.getValues().stream().map(v -> new ProductAttributeValue(v.getValue(), v.getSort())).collect(Collectors.toSet()));
        		return a;
        	}).toList();
        	p.setAttributes(attributes);
        }
        if (entity.getVariants() != null) {
        	p.setVariants(entity.getVariants().stream().map(ProductMapper::toVariant).toList());
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
        p.setCreatedAt(entity.getCreatedAt());
        p.setWithVariant(entity.isWithVariant());
        p.setThumbnail(entity.getThumbnail());
        p.setVideoUrl(entity.getVideoUrl());
        if (entity.getDiscount() != null) {
            p.setDiscount(DiscountMapper.toDomain(entity.getDiscount()));
        }
        p.setShop(ShopMapper.toDomainCompat(entity.getShop()));
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

    public static ProductVariant toVariant(ProductVariantEntity entity) {
        var pv = new ProductVariant();
        pv.setId(entity.getId());
        pv.setSku(entity.getSku());
        pv.setPrice(entity.getPrice());
        pv.setStockLeft(entity.getStockLeft());
        var attributes = entity.getAttributes().stream().map(a -> new ProductVariantAttribute(a.getValue(), a.getSort())).collect(Collectors.toSet());
        pv.setAttributes(attributes);
        return pv;
    }

}
