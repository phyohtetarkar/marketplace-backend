package com.shoppingcenter.app.controller.authentication.dto;

import com.shoppingcenter.app.controller.user.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDTO {

    private String accessToken;

    private String refreshToken;

    private UserDTO user;

}
