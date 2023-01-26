package com.shoppingcenter.service.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getUserId() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

}
