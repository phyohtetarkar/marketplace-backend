package com.shoppingcenter.app.controller.category.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shoppingcenter.core.PageData;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private int id;

    private String name;

    private String slug;

    @Schema(accessMode = AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private String image;

    @Schema(accessMode = AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private int level;

    @Schema(accessMode = AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private CategoryDTO category;

    @Schema(accessMode = AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private List<CategoryDTO> children;

    @JsonProperty
    private Integer categoryId;

    @Schema(accessMode = AccessMode.READ_ONLY)
    @JsonProperty(access = Access.READ_ONLY)
    private long createdAt;

    @Schema(accessMode = AccessMode.WRITE_ONLY)
    @JsonProperty(access = Access.WRITE_ONLY)
    private MultipartFile file;

    public static Type listType() {
        return new TypeToken<List<CategoryDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageData<CategoryDTO>>() {
        }.getType();
    }

}
