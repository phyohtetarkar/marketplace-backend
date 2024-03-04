package com.marketplace.api.vendor.product;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.ProductImageSerializer;
import com.marketplace.api.AuditDTO;
import com.marketplace.api.consumer.category.CategoryDTO;
import com.marketplace.api.consumer.product.DiscountDTO;
import com.marketplace.api.consumer.product.ProductAttributeDTO;
import com.marketplace.api.consumer.product.ProductImageDTO;
import com.marketplace.api.consumer.product.ProductVariantDTO;
import com.marketplace.domain.product.Product;

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

    private boolean available;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    @JsonSerialize(using = ProductImageSerializer.class)
    private String thumbnail;

    private String description;
    
    private String videoEmbed;
    
    private Product.Status status;

    private List<ProductAttributeDTO> attributes;

    private List<ProductVariantDTO> variants;

    private List<ProductImageDTO> images;

    private DiscountDTO discount;

    private CategoryDTO category;

    private AuditDTO audit;
}
