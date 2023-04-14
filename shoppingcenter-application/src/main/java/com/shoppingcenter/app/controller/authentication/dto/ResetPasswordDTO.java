package com.shoppingcenter.app.controller.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    private String otp;

    private String phone;

    private String password;

}
