package com.shoppingcenter.data.product;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.discount.DiscountMapper;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.data.variant.ProductVariantEntity;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.ProductOption;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.ProductVariantOption;
import com.shoppingcenter.search.product.ProductDocument;
import com.shoppingcenter.search.product.ProductImageDocument;

import lombok.var;

public class ProductMapper {

    private static String imageBaseUrl(String baseUrl) {
        if (baseUrl != null) {
            return String.format("%s%s/", baseUrl, "product");
        }

        return "";
    }

    public static Product toDomain(ProductEntity entity, String baseUrl) {
        String imageBaseUrl = imageBaseUrl(baseUrl);
        var p = toDomainCompat(entity, baseUrl);
        p.setDescription(entity.getDescription());
        if (entity.getImages() != null) {
            p.setImages(entity.getImages().stream().map(e -> toImage(e,
                    imageBaseUrl))
                    .collect(Collectors.toList()));
        }
        p.setCategory(CategoryMapper.toDomain(entity.getCategory(), baseUrl));
        p.setOptions(entity.getOptions().stream().map(ProductMapper::toOption).collect(Collectors.toList()));
        return p;
    }

    public static Product toDomainCompat(ProductEntity entity, String baseUrl) {
        String imageBaseUrl = imageBaseUrl(baseUrl);
        var p = new Product();
        p.setId(entity.getId());
        p.setName(entity.getName());
        p.setSlug(entity.getSlug());
        p.setBrand(entity.getBrand());
        p.setPrice(entity.getPrice());
        p.setStockLeft(entity.getStockLeft());
        p.setFeatured(entity.isFeatured());
        p.setNewArrival(entity.isNewArrival());
        p.setCategory(CategoryMapper.toDomainCompat(entity.getCategory(), baseUrl));
        p.setShop(ShopMapper.toDomainCompat(entity.getShop(), baseUrl));
        p.setCreatedAt(entity.getCreatedAt());
        p.setStatus(Product.Status.valueOf(entity.getStatus()));
        p.setWithVariant(entity.isWithVariant());
        var images = entity.getImages();
        if (images != null && images.size() > 0) {
            var thumbnail = images.stream()
                    .filter(ProductImageEntity::isThumbnail)
                    .findFirst()
                    .map(e -> imageBaseUrl + e.getName())
                    .orElseGet(() -> imageBaseUrl + images.get(0).getName());
            p.setThumbnail(thumbnail);
        }
        if (entity.getDiscount() != null) {
            p.setDiscount(DiscountMapper.toDomain(entity.getDiscount()));
        }
        return p;
    }

    public static Product toDomainCompat(ProductDocument document, String baseUrl) {
        String imageBaseUrl = imageBaseUrl(baseUrl);
        var p = new Product();
        p.setId(document.getEntityId());
        p.setName(document.getName());
        p.setSlug(document.getSlug());
        p.setBrand(document.getBrand());
        p.setPrice(document.getPrice());
        p.setStockLeft(document.getStockLeft());
        p.setFeatured(document.isFeatured());
        p.setNewArrival(document.isNewArrival());
        p.setCategory(CategoryMapper.toDomainCompat(document.getCategory(), baseUrl));
        p.setShop(ShopMapper.toDomainCompat(document.getShop(), baseUrl));
        p.setStatus(Product.Status.valueOf(document.getStatus()));
        p.setWithVariant(document.isWithVariant());
        p.setCreatedAt(document.getCreatedAt());
        var images = document.getImages();
        if (images != null && images.size() > 0) {
            var thumbnail = images.stream()
                    .filter(ProductImageDocument::isThumbnail)
                    .findFirst()
                    .map(e -> imageBaseUrl + e.getName())
                    .orElseGet(() -> imageBaseUrl + images.get(0).getName());
            p.setThumbnail(thumbnail);
        }
        if (document.getDiscount() != null) {
            p.setDiscount(DiscountMapper.toDomain(document.getDiscount()));
        }
        return p;
    }

    public static ProductImage toImage(ProductImageEntity entity, String baseUrl) {
        var image = new ProductImage();
        image.setId(entity.getId());
        image.setThumbnail(entity.isThumbnail());
        image.setName(entity.getName());
        if (Utils.hasText(entity.getName())) {
            image.setUrl(baseUrl + entity.getName());
        }
        return image;
    }

    public static ProductOption toOption(ProductOptionEntity entity) {
        var op = new ProductOption();
        op.setId(entity.getId());
        op.setName(entity.getName());
        op.setPosition(entity.getPosition());
        return op;
    }

    public static ProductVariant toVariant(ProductVariantEntity entity, ObjectMapper mapper) {
        var pv = new ProductVariant();
        pv.setId(entity.getId());
        pv.setTitle(entity.getTitle());
        pv.setSku(entity.getSku());
        pv.setPrice(entity.getPrice());
        pv.setStockLeft(entity.getStockLeft());
        if (Utils.hasText(entity.getOptions())) {
            try {
                pv.setOptions(mapper.readValue(entity.getOptions(), new TypeReference<List<ProductVariantOption>>() {
                }));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return pv;
    }

}
