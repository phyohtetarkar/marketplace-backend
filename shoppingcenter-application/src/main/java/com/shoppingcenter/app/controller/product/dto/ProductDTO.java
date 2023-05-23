package com.shoppingcenter.app.controller.product.dto;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.discount.dto.DiscountDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.domain.product.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private BigDecimal price;

    private int stockLeft;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    private String thumbnail;

    private String description;
    
    private String videoUrl;

    private boolean disabled;
    
    private Product.Status status;

    private List<ProductAttributeDTO> attributes;

    private List<ProductVariantDTO> variants;

    private List<ProductImageDTO> images;

    private DiscountDTO discount;

    private CategoryDTO category;

    private ShopDTO shop;

    private long createdAt;

    public static Type listType() {
        return new TypeToken<List<ProductDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<ProductDTO>>() {
        }.getType();
    }
}
