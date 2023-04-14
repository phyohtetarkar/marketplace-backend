package com.shoppingcenter.app.controller.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {

    private String otp;

    private String fullName;

    private String username;

    private String password;

}
