package com.shoppingcenter.search.shop;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "shops")
public class ShopDocument {

    @Id
    private String id;

    private long entityId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String slug;

    @Field(type = FieldType.Text)
    private String headline;

    private double rating;

    @Field(type = FieldType.Keyword)
    private String logo;

    @Field(type = FieldType.Keyword)
    private String cover;

    @Field(type = FieldType.Keyword)
    private String status;

    private long createdAt;

}
