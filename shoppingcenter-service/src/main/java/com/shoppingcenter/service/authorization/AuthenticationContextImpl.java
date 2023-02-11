package com.shoppingcenter.service.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.service.user.model.User;
import com.shoppingcenter.service.user.model.User.Role;

@Component
public class AuthenticationContextImpl implements AuthenticationContext {

    @Autowired
    private UserRepo userRepo;

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
        return "";
    }

    @Override
    public Role getRole() {
        String userId = getUserId();
        if (StringUtils.hasText(userId)) {
            return userRepo.findById(userId).map(e -> User.Role.valueOf(e.getRole())).orElse(null);
        }
        return null;
    }

}
