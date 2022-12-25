package com.shoppingcenter.core.shop.model;

import com.shoppingcenter.data.shop.ShopMemberEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMember {

    private String userId;

    private long shopId;

    private String name;

    private String phone;

    private ShopMemberEntity.Role role;

    public static ShopMember create(ShopMemberEntity entity) {
        ShopMember sm = new ShopMember();
        sm.setUserId(entity.getId().getUserId());
        sm.setShopId(entity.getId().getShopId());
        sm.setName(entity.getUser().getName());
        sm.setPhone(entity.getUser().getPhone());
        sm.setRole(entity.getRole());
        return sm;
    }
}
