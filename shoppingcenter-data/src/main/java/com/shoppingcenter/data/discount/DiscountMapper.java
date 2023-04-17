package com.shoppingcenter.data.discount;

import com.shoppingcenter.domain.discount.Discount;

public class DiscountMapper {

    public static Discount toDomain(DiscountEntity entity) {
        var d = new Discount();
        d.setId(entity.getId());
        d.setTitle(entity.getTitle());
        d.setValue(entity.getValue());
        d.setType(entity.getType());
        d.setCreatedAt(entity.getCreatedAt());
        return d;
    }

}
