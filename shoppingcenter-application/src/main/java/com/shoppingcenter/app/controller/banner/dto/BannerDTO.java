package com.shoppingcenter.app.controller.banner.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerDTO {
    private int id;

    private String image;

    private String link;

    private int position;

    private long createdAt;

    public static Type listType() {
        return new TypeToken<List<BannerDTO>>() {
        }.getType();
    }
}
