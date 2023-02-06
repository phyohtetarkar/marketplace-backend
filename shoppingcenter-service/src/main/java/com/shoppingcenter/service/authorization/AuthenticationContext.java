package com.shoppingcenter.service.authorization;

import org.springframework.security.core.Authentication;

import com.shoppingcenter.service.user.model.User;

public interface AuthenticationContext {
    Authentication getAuthentication();

    String getUserId();

    User.Role getRole();
}
