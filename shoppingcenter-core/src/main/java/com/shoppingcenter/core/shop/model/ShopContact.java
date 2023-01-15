package com.shoppingcenter.core.shop.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.shoppingcenter.data.shop.ShopContactEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopContact {
    private long id;

    private List<String> phones;

    private String address;

    private Double latitude;

    private Double longitude;

    private long shopId;

    public static ShopContact create(ShopContactEntity entity) {
        ShopContact contact = new ShopContact();
        if (entity == null) {
            return contact;
        }
        contact.setId(entity.getId());
        contact.setAddress(entity.getAddress());
        contact.setLatitude(entity.getLatitude());
        contact.setLongitude(entity.getLongitude());

        if (StringUtils.hasText(entity.getPhones())) {
            contact.setPhones(Arrays.asList(entity.getPhones().split(",")));
        }
        return contact;
    }
}
