package com.shoppingcenter.app.controller.shop.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.service.shop.model.ShopMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMemberDTO {

    private long id;

    private ShopMember.Role role;

    private UserDTO member;

    public static Type listType() {
        return new TypeToken<List<ShopMemberDTO>>() {
        }.getType();
    }
}
