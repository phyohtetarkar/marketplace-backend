package com.marketplace.api.vendor.shop;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.marketplace.api.consumer.user.UserDTO;
import com.marketplace.domain.shop.ShopMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMemberDTO {

    private ShopMember.Role role;

    private UserDTO member;

    public static Type listType() {
        return new TypeToken<List<ShopMemberDTO>>() {
        }.getType();
    }
}
