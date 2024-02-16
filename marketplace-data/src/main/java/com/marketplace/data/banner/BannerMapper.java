package com.marketplace.data.banner;

import com.marketplace.data.AuditMapper;
import com.marketplace.domain.banner.Banner;

public interface BannerMapper {

    public static Banner toDomain(BannerEntity entity) {
        Banner b = new Banner();
        b.setId(entity.getId());
        b.setLink(entity.getLink());
        b.setPosition(entity.getPosition());
        b.setImage(entity.getImage());
        b.setAudit(AuditMapper.from(entity));
        return b;
    }

}
