package com.shoppingcenter.app.controller.user.dto;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private long id;

    private String name;

    private String phone;

    private String email;

    private String image;

    private User.Role role;

    private long createdAt;

    private boolean disabled;
    
    private boolean verified;
    
    private List<UserPermissionDTO> permissions;

    public static Type listType() {
        return new TypeToken<List<UserDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<UserDTO>>() {
        }.getType();
    }
}
