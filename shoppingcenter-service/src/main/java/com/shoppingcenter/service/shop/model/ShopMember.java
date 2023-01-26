package com.shoppingcenter.service.shop.model;

import com.shoppingcenter.data.shop.ShopMemberEntity;
import com.shoppingcenter.service.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMember {

    public enum Role {
        OWNER, ADMIN, VIEWER
    }

    private long id;

    private String userId;

    private long shopId;

    private Role role;

    private User member;

    public ShopMember() {
        this.role = Role.VIEWER;
    }

    public static ShopMember create(ShopMemberEntity entity, String baseUrl) {
        ShopMember sm = new ShopMember();
        sm.setId(entity.getId());
        sm.setUserId(entity.getUser().getId());
        sm.setShopId(entity.getShop().getId());
        sm.setMember(User.create(entity.getUser(), baseUrl));
        sm.setRole(Role.valueOf(entity.getRole()));
        return sm;
    }
}
