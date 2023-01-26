package com.shoppingcenter.service.authorization;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    String getUserId();
}
