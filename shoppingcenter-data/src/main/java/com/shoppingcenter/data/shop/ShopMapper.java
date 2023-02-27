package com.shoppingcenter.data.shop;

import java.util.Arrays;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopContact;

public class ShopMapper {

    public static Shop toDomain(ShopEntity entity, String baseUrl) {
        var s = toDomainCompat(entity, baseUrl);
        s.setAbout(entity.getAbout());
        s.setContact(toContact(entity.getContact()));
        return s;
    }

    public static Shop toDomainCompat(ShopEntity entity, String baseUrl) {
        String imageBaseUrl = imageBaseUrl(entity.getSlug(), baseUrl);
        var s = new Shop();
        s.setId(entity.getId());
        s.setName(entity.getName());
        s.setSlug(entity.getSlug());
        s.setHeadline(entity.getHeadline());
        s.setFeatured(entity.isFeatured());
        s.setRating(entity.getRating());
        s.setCreatedAt(entity.getCreatedAt());
        s.setStatus(Shop.Status.valueOf(entity.getStatus()));
        if (Utils.hasText(entity.getLogo())) {
            s.setLogo(imageBaseUrl + entity.getLogo());
        }

        if (Utils.hasText(entity.getCover())) {
            s.setCover(imageBaseUrl + entity.getCover());
        }
        return s;
    }

    private static String imageBaseUrl(String slug, String baseUrl) {
        return String.format("%s%s/", baseUrl, "shop");
    }

    public static ShopContact toContact(ShopContactEntity entity) {
        var contact = new ShopContact();
        if (entity == null) {
            return contact;
        }
        contact.setId(entity.getId());
        contact.setAddress(entity.getAddress());
        contact.setLatitude(entity.getLatitude());
        contact.setLongitude(entity.getLongitude());

        if (Utils.hasText(entity.getPhones())) {
            contact.setPhones(Arrays.asList(entity.getPhones().split(",")));
        }
        return contact;
    }
}
