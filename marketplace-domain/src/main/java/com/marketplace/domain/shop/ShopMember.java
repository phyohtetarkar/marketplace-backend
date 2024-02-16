package com.marketplace.domain.shop;

import com.marketplace.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMember {

    public enum Role {
        OWNER, ADMIN
    }

    private Role role;

    private User member;

    public ShopMember() {
        this.role = Role.ADMIN;
    }
}
