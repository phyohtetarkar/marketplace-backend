package com.shoppingcenter.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.User.Role;

@Component
public class AuthenticationContextImpl implements AuthenticationContext {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User getCurrentUser() {
        return userRepo.findById(getUserId())
                .map(e -> UserMapper.toDomain(e, null))
                .orElseThrow(() -> new AccessDeniedException("Not authenticated"));
    }

    @Override
    public String getUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "";
    }

    @Override
    public Role getRole() {
        return getCurrentUser().getRole();
    }

    @Override
    public boolean isSiteAdmin() {
        var role = getRole();
        return role == Role.ADMIN || role == Role.OWNER;
    }

}
