package com.shoppingcenter.domain.user;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    public enum Role {
        USER, STAFF, ADMIN, OWNER
    }

    private long id;

    private String name;

    private String phone;

    private String password;

    private String email;

    private String image;

    private Role role;

    private boolean disabled;
    
    private boolean verified;
    
    private List<UserPermission> permissions;

    private long createdAt;

}
