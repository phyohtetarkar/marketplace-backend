package com.shoppingcenter.search.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import com.shoppingcenter.search.shop.ShopDocument;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "products")
public class ProductDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    @CompletionField
    private Completion suggest;

    @Field(type = FieldType.Keyword)
    private String slug;

    @Field(type = FieldType.Text)
    private String brand;

    private BigDecimal price;

    private boolean hidden;

    private boolean disabled;

    private long createdAt;

    @Field(type = FieldType.Nested)
    private CategoryDocument category;

    @Field(type = FieldType.Nested)
    private List<CategoryDocument> categories;

    @Field(type = FieldType.Nested)
    private ShopDocument shop;

}
