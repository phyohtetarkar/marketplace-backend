package com.marketplace.api.consumer.user;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.UserImageSerializer;
import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private long id;

    private String name;

    private String phone;

    private String email;

    @JsonSerialize(using = UserImageSerializer.class)
    private String image;

    private User.Role role;

    private boolean disabled;
    
    private List<User.Permission> permissions;

    public static Type listType() {
        return new TypeToken<List<UserDTO>>() {
        }.getType();
    }

    public static Type pageType() {
        return new TypeToken<PageDataDTO<UserDTO>>() {
        }.getType();
    }
}
