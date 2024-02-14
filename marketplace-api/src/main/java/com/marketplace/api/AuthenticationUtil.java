package com.marketplace.api;

import org.springframework.security.core.context.SecurityContextHolder;

import com.marketplace.domain.user.User;

public interface AuthenticationUtil {

    public static User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	
        	if (authentication.getPrincipal() instanceof UserPrincipal up) {
        		return up.getUser();
        	}
        }

//        throw new AccessDeniedException("Not authenticated");
        return null;
    }
    
    public static long getAuthenticatedUserId() {
        var user = getAuthenticatedUser();
        return user != null ? user.getId() : 0;
    }
    
}
