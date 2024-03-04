package com.marketplace.api.admin.user;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.UserImageSerializer;
import com.marketplace.domain.Audit;
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
    
    private Audit audit;
}
