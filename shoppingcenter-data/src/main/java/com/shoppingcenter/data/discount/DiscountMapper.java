package com.shoppingcenter.data.discount;

import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.search.product.DiscountDocument;

public class DiscountMapper {

    public static Discount toDomain(DiscountEntity entity) {
        var d = new Discount();
        d.setId(entity.getId());
        d.setShopId(entity.getShop().getId());
        d.setTitle(entity.getTitle());
        d.setValue(entity.getValue());
        d.setType(Discount.Type.valueOf(entity.getType()));
        d.setCreatedAt(entity.getCreatedAt());
        return d;
    }

    public static Discount toDomain(DiscountDocument document) {
        var d = new Discount();
        d.setId(document.getEntityId());
        d.setTitle(document.getTitle());
        d.setValue(document.getValue());
        d.setType(Discount.Type.valueOf(document.getType()));
        return d;
    }

    public static DiscountDocument toDocument(Discount discount) {
        var document = new DiscountDocument();
        document.setEntityId(discount.getId());
        document.setTitle(discount.getTitle());
        document.setValue(discount.getValue());
        document.setType(discount.getType().name());
        return document;
    }

}
