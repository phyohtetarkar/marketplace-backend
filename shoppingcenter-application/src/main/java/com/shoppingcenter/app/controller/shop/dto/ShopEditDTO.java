package com.shoppingcenter.app.controller.shop.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopEditDTO {
    private long id;

    private String name;

    private String slug;

    private String headline;

    private String about;

    private String address;

    private long subscriptionPlanId;

    private MultipartFile logoImage;

    private MultipartFile coverImage;

}
