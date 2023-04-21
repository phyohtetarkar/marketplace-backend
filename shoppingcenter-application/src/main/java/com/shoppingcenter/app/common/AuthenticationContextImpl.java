package com.shoppingcenter.app.common;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.shoppingcenter.app.security.UserPrincipal;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.User.Role;

@Component
public class AuthenticationContextImpl implements AuthenticationContext {

    @Override
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	
        	if (authentication.getPrincipal() instanceof UserPrincipal up) {
        		return up.getUser();
        	}
        }

        throw new AccessDeniedException("Not authenticated");
    }

    @Override
    public long getUserId() {
        return getCurrentUser().getId();
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
