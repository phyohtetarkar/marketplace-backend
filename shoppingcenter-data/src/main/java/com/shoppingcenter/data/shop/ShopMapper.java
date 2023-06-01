package com.shoppingcenter.data.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.suggest.Completion;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.search.shop.ShopDocument;

public class ShopMapper {

//    private static String imageBaseUrl(String baseUrl) {
//        if (baseUrl != null) {
//            return String.format("%s%s/", baseUrl, "shop");
//        }
//
//        return "";
//    }

    public static Shop toDomainCompat(ShopEntity entity) {
        var s = new Shop();
        s.setId(entity.getId());
        s.setName(entity.getName());
        s.setSlug(entity.getSlug());
        s.setHeadline(entity.getHeadline());
        s.setFeatured(entity.isFeatured());
        s.setRating(entity.getRating().doubleValue());
        s.setCreatedAt(entity.getCreatedAt());
        s.setStatus(entity.getStatus());
        s.setLogo(entity.getLogo());
        s.setCover(entity.getCover());
        s.setExpiredAt(entity.getExpiredAt());
        return s;
    }

    public static Shop toDomain(ShopEntity entity) {
        var s = toDomainCompat(entity);
        s.setAbout(entity.getAbout());
        if (entity.getContact() != null) {
        	s.setContact(toContact(entity.getContact()));
        }
        return s;
    }

    public static ShopContact toContact(ShopContactEntity entity) {
        var contact = new ShopContact();
        if (entity == null) {
            return contact;
        }
        contact.setShopId(entity.getId());
        contact.setAddress(entity.getAddress());
        contact.setLatitude(entity.getLatitude());
        contact.setLongitude(entity.getLongitude());

        if (Utils.hasText(entity.getPhones())) {
            contact.setPhones(Arrays.asList(entity.getPhones().split(",")));
        }
        return contact;
    }

    public static Shop toDomainCompat(ShopDocument document, String baseUrl) {
        var s = new Shop();
        s.setId(document.getId());
        s.setName(document.getName());
        s.setSlug(document.getSlug());
        s.setHeadline(document.getHeadline());
        return s;
    }

    public static ShopDocument toDocument(Shop shop) {
        var document = new ShopDocument();
        document.setId(shop.getId());
        document.setName(shop.getName());
        document.setSlug(shop.getSlug());
        document.setHeadline(shop.getHeadline());
        
        var splittedNames = Arrays.asList(document.getName().split("\\s+"));
        var splittedHeadlines = Arrays.asList(document.getHeadline().split("\\s+"));
        var lenN = splittedNames.size();
        var lenH = splittedHeadlines.size();
        var suggestInputs = new ArrayList<String>();

        for (var i = 0; i < lenN; i++) {
            var input = splittedNames.stream().skip(i).collect(Collectors.joining(" "));
            if (input.length() > 1) {
                suggestInputs.add(input);
            }
        }

        for (var i = 0; i < lenH; i++) {
            var input = splittedHeadlines.stream().skip(i).collect(Collectors.joining(" "));
            if (input.length() > 1) {
                suggestInputs.add(input);
            }
        }

        var suggest = new Completion(suggestInputs);
        document.setSuggest(suggest);
        return document;
    }

    public static ShopDocument toDocument(ShopEntity shop) {
        var document = new ShopDocument();
        document.setId(shop.getId());
        document.setName(shop.getName());
        document.setSlug(shop.getSlug());
        document.setHeadline(shop.getHeadline());
        return document;
    }

}
