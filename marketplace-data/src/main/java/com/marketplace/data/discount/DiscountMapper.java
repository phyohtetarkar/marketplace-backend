package com.marketplace.data.discount;

import com.marketplace.data.AuditMapper;
import com.marketplace.domain.discount.Discount;

public interface DiscountMapper {

    public static Discount toDomain(DiscountEntity entity) {
        var d = new Discount();
        d.setId(entity.getId());
        d.setTitle(entity.getTitle());
        d.setValue(entity.getValue());
        d.setType(entity.getType());
        d.setAudit(AuditMapper.from(entity));
        d.setShopId(entity.getShop().getId());
        return d;
    }

}
