package com.shoppingcenter.app.controller.user.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.data.user.UserEntity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String id;

    private String name;

    private String phone;

    private String email;

    private String image;

    private Role role;

    private long createdAt;

    public static Type listType() {
        return new TypeToken<List<UserDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageData<UserDTO>>() {
        }.getType();
    }
}