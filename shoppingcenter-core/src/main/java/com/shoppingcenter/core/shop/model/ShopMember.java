package com.shoppingcenter.core.shop.model;

import com.shoppingcenter.core.user.model.User;
import com.shoppingcenter.data.shop.ShopMemberEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMember {

    private String id;

    private String userId;

    private long shopId;

    private ShopMemberEntity.Role role;

    private User member;

    public static ShopMember create(ShopMemberEntity entity, String baseUrl) {
        ShopMember sm = new ShopMember();
        sm.setId(entity.getId());
        sm.setUserId(entity.getUser().getId());
        sm.setShopId(entity.getShop().getId());
        sm.setMember(User.create(entity.getUser(), baseUrl));
        sm.setRole(entity.getRole());
        return sm;
    }
}
