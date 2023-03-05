package com.shoppingcenter.data.shop;

import java.util.Arrays;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.search.shop.ShopDocument;

public class ShopMapper {

    private static String imageBaseUrl(String baseUrl) {
        if (baseUrl != null) {
            return String.format("%s%s/", baseUrl, "shop");
        }

        return "";
    }

    public static Shop toDomainCompat(ShopEntity entity, String baseUrl) {
        String imageBaseUrl = imageBaseUrl(baseUrl);
        var s = new Shop();
        s.setId(entity.getId());
        s.setName(entity.getName());
        s.setSlug(entity.getSlug());
        s.setHeadline(entity.getHeadline());
        s.setFeatured(entity.isFeatured());
        s.setRating(entity.getRating());
        s.setCreatedAt(entity.getCreatedAt());
        s.setStatus(Shop.Status.valueOf(entity.getStatus()));
        s.setLogo(entity.getLogo());
        s.setCover(entity.getCover());
        if (Utils.hasText(entity.getLogo())) {
            s.setLogoUrl(imageBaseUrl + entity.getLogo());
        }

        if (Utils.hasText(entity.getCover())) {
            s.setCoverUrl(imageBaseUrl + entity.getCover());
        }
        return s;
    }

    public static Shop toDomainCompat(ShopDocument document, String baseUrl) {
        String imageBaseUrl = imageBaseUrl(baseUrl);
        var s = new Shop();
        s.setId(document.getId());
        s.setName(document.getName());
        s.setSlug(document.getSlug());
        s.setHeadline(document.getHeadline());
        s.setCreatedAt(document.getCreatedAt());
        s.setStatus(Shop.Status.valueOf(document.getStatus()));
        s.setLogo(document.getLogo());

        if (Utils.hasText(document.getLogo())) {
            s.setLogoUrl(imageBaseUrl + document.getLogo());
        }
        return s;
    }

    public static Shop toDomain(ShopEntity entity, String baseUrl) {
        var s = toDomainCompat(entity, baseUrl);
        s.setAbout(entity.getAbout());
        s.setContact(toContact(entity.getContact()));
        return s;
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

    public static ShopDocument toDocument(Shop shop) {
        var document = new ShopDocument();
        document.setId(shop.getId());
        document.setName(shop.getName());
        document.setSlug(shop.getSlug());
        document.setHeadline(shop.getHeadline());
        document.setCreatedAt(shop.getCreatedAt());
        document.setStatus(shop.getStatus().name());
        document.setLogo(shop.getLogo());
        return document;
    }

    public static ShopDocument toDocument(ShopEntity shop) {
        var document = new ShopDocument();
        document.setId(shop.getId());
        document.setName(shop.getName());
        document.setSlug(shop.getSlug());
        document.setHeadline(shop.getHeadline());
        document.setCreatedAt(shop.getCreatedAt());
        document.setStatus(shop.getStatus());
        document.setLogo(shop.getLogo());
        return document;
    }

}
