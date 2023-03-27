package com.shoppingcenter.search.shop;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionContext;
import org.springframework.data.elasticsearch.annotations.CompletionContext.ContextMappingType;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "shops")
public class ShopDocument {

    @Id
    private long id;

    @Field(type = FieldType.Text)
    private String name;

    @CompletionField(contexts = {
            @CompletionContext(name = "status", type = ContextMappingType.CATEGORY, path = "status")
    })
    private Completion suggest;

    @Field(type = FieldType.Keyword)
    private String slug;

    @Field(type = FieldType.Text)
    private String headline;

    @Field(type = FieldType.Keyword)
    private String status;

    private long createdAt;

}
