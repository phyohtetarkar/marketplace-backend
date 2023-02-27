package com.shoppingcenter.app.controller.user.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.user.User;

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

    private User.Role role;

    private long createdAt;

    private boolean disabled;

    public static Type listType() {
        return new TypeToken<List<UserDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageData<UserDTO>>() {
        }.getType();
    }
}
