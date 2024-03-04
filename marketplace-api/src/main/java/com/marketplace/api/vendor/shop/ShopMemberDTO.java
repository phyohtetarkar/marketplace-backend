package com.marketplace.api.vendor.shop;

import com.marketplace.api.consumer.user.UserDTO;
import com.marketplace.domain.shop.ShopMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMemberDTO {

    private ShopMember.Role role;

    private UserDTO member;
}
