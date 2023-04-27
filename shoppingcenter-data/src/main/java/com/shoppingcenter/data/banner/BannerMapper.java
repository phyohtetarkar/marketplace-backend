package com.shoppingcenter.data.banner;

import com.shoppingcenter.domain.banner.Banner;

public class BannerMapper {

    public static Banner toDomain(BannerEntity entity) {
        Banner b = new Banner();
        b.setId(entity.getId());
        b.setLink(entity.getLink());
        b.setPosition(entity.getPosition());
        b.setCreatedAt(entity.getCreatedAt());
        b.setImage(entity.getImage());
        // if (Utils.hasText(entity.getImage())) {
        // b.setImage(baseUrl + "banner/" + entity.getImage());
        // }
        return b;
    }

}
