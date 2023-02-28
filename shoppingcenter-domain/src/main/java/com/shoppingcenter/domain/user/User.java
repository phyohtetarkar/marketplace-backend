package com.shoppingcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    public enum Role {
        USER, ADMIN, OWNER
    }

    private String id;

    private String name;

    private String phone;

    private String email;

    private String image;

    private Role role;

    private boolean disabled;

    private boolean confirmed;

    private Long createdAt;

}
