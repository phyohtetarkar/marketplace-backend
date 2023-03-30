package com.shoppingcenter.data.product;

import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.discount.DiscountMapper;
import com.shoppingcenter.data.product.variant.ProductVariantEntity;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.product.ProductOption;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.ProductVariantOption;
import com.shoppingcenter.search.product.ProductDocument;

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
        p.setCategory(CategoryMapper.toDomain(entity.getCategory(), baseUrl));
        if (entity.getOptions() != null) {
            p.setOptions(entity.getOptions().stream().map(ProductMapper::toOption).collect(Collectors.toList()));
        }
        var images = entity.getImages();
        if (images != null) {
            p.setImages(images.stream().map(e -> toImage(e, imageBaseUrl)).toList());
        }
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
        p.setHidden(entity.isHidden());
        p.setDisabled(entity.isDisabled());
        p.setNewArrival(entity.isNewArrival());
        p.setCategory(CategoryMapper.toDomainCompat(entity.getCategory(), baseUrl));
        p.setShop(ShopMapper.toDomainCompat(entity.getShop(), baseUrl));
        p.setCreatedAt(entity.getCreatedAt());
        p.setWithVariant(entity.isWithVariant());

        if (StringUtils.hasText(entity.getThumbnail())) {
            p.setThumbnail(imageBaseUrl + entity.getThumbnail());
        }

        if (entity.getDiscount() != null) {
            p.setDiscount(DiscountMapper.toDomain(entity.getDiscount()));
        }
        return p;
    }

    public static Product toDomainCompat(ProductDocument document, String baseUrl) {
        var p = new Product();
        p.setId(document.getId());
        p.setName(document.getName());
        p.setSlug(document.getSlug());
        p.setBrand(document.getBrand());
        p.setPrice(document.getPrice());
        p.setCategory(CategoryMapper.toDomainCompat(document.getCategory(),
                baseUrl));
        p.setShop(ShopMapper.toDomainCompat(document.getShop(), baseUrl));
        p.setCreatedAt(document.getCreatedAt());
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
        // if (Utils.hasText(entity.getOptions())) {
        // try {
        // pv.setOptions(mapper.readValue(entity.getOptions(), new
        // TypeReference<List<ProductVariantOption>>() {
        // }));
        // } catch (JsonMappingException e) {
        // e.printStackTrace();
        // } catch (JsonProcessingException e) {
        // e.printStackTrace();
        // }
        // }

        return pv;
    }

}
