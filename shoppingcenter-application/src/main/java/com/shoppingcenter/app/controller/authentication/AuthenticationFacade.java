package com.shoppingcenter.app.controller.authentication;

import com.shoppingcenter.app.controller.authentication.dto.AuthenticationDTO;
import com.shoppingcenter.app.controller.authentication.dto.LoginDTO;
import com.shoppingcenter.app.controller.authentication.dto.SignUpDTO;

public interface AuthenticationFacade {

    AuthenticationDTO login(LoginDTO dto);

    AuthenticationDTO signUp(SignUpDTO dto);

    AuthenticationDTO refresh(String refreshToken);

    void sendOTP(String phone);

}
