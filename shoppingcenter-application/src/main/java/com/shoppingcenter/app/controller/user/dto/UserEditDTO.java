package com.shoppingcenter.app.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDTO {

    @JsonIgnore
    private String id;

    private String name;

    private String phone;

    private String email;
}
