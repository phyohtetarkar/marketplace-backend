package com.shoppingcenter.search.product;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.shoppingcenter.search.shop.ShopDocument;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "products")
public class ProductDocument {

    @Id
    private String id;

    private long entityId;

    @Field(type = FieldType.Keyword)
    private String sku;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String slug;

    @Field(type = FieldType.Text)
    private String brand;

    private double price;

    private int stockLeft;

    @Field(type = FieldType.Keyword)
    private String thumbnail;

    private boolean featured;

    private boolean newArrival;

    private boolean withVariant;

    @Field(type = FieldType.Keyword)
    private String status;

    private long createdAt;

    @Field(type = FieldType.Nested)
    private List<ProductOptionDocument> options;

    @Field(type = FieldType.Nested)
    private List<ProductVariantDocument> variants;

    @Field(type = FieldType.Nested)
    private List<ProductImageDocument> images;

    @Field(type = FieldType.Nested)
    private DiscountDocument discount;

    @Field(type = FieldType.Nested)
    private CategoryDocument category;

    @Field(type = FieldType.Nested)
    private List<CategoryDocument> categories;

    @Field(type = FieldType.Nested)
    private ShopDocument shop;

}
