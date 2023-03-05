package com.shoppingcenter.data.banner;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.banner.Banner;

public class BannerMapper {

    public static Banner toDomain(BannerEntity entity, String baseUrl) {
        Banner b = new Banner();
        b.setId(entity.getId());
        b.setLink(entity.getLink());
        b.setPosition(entity.getPosition());
        b.setCreatedAt(entity.getCreatedAt());
        b.setImage(entity.getImage());
        if (Utils.hasText(entity.getImage())) {
            b.setImageUrl(baseUrl + "banner/" + entity.getImage());
        }
        return b;
    }

}