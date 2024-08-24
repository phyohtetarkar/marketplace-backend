package com.marketplace.data.product;

import java.util.stream.Collectors;

import com.marketplace.data.AuditMapper;
import com.marketplace.data.category.CategoryMapper;
import com.marketplace.data.discount.DiscountMapper;
import com.marketplace.data.shop.ShopMapper;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductAttribute;
import com.marketplace.domain.product.ProductImage;
import com.marketplace.domain.product.ProductVariant;
import com.marketplace.domain.product.ProductVariantAttribute;

public interface ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        var p = toDomainCompat(entity);
        p.setDescription(entity.getDescription());
        p.setVideoEmbed(entity.getVideoEmbed());
        p.setShop(ShopMapper.toDomain(entity.getShop()));
        if (entity.getAttributes() != null) {
        	var attributes = entity.getAttributes().stream().map(e -> {
        		var a = new ProductAttribute();
        		a.setName(e.getName());
        		a.setSort(e.getSort());
        		return a;
        	}).toList();
        	p.setAttributes(attributes);
        }
        if (entity.getVariants() != null) {
        	p.setVariants(entity.getVariants().stream().map(ProductMapper::toVariant).toList());
        }
        var images = entity.getImages();
        if (images != null) {
            p.setImages(images.stream().map(ProductMapper::toImage).toList());
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
        p.setAvailable(entity.isAvailable());
        p.setFeatured(entity.isFeatured());
        p.setStatus(entity.getStatus());
        p.setNewArrival(entity.isNewArrival());
        p.setWithVariant(entity.isWithVariant());
        p.setThumbnail(entity.getThumbnail());
        p.setDeleted(entity.isDeleted());
        if (entity.getDiscount() != null) {
            p.setDiscount(DiscountMapper.toDomain(entity.getDiscount()));
        }
        p.setCategory(CategoryMapper.toDomain(entity.getCategory()));
        p.setShop(ShopMapper.toDomainCompat(entity.getShop()));
        p.setAudit(AuditMapper.from(entity));
        return p;
    }

    public static ProductImage toImage(ProductImageEntity entity) {
        var image = new ProductImage();
        image.setId(entity.getId());
        image.setThumbnail(entity.isThumbnail());
        image.setSize(entity.getSize());
        image.setName(entity.getName());
        return image;
    }

    public static ProductVariant toVariant(ProductVariantEntity entity) {
        var pv = new ProductVariant();
        pv.setId(entity.getId());
        pv.setSku(entity.getSku());
        pv.setPrice(entity.getPrice());
        pv.setAvailable(entity.isAvailable());
        if (entity.getAttributes() != null) {
        	var attributes = entity.getAttributes().stream().map(a -> {
            	var va = new ProductVariantAttribute();
            	va.setAttribute(a.getAttribute());
            	va.setValue(a.getValue());
            	va.setSort(a.getSort());
            	va.setVSort(a.getVSort());
            	return va;
            }).collect(Collectors.toSet());
            pv.setAttributes(attributes);
        }
        return pv;
    }

}
