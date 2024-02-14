package com.marketplace.api;

import org.springframework.security.core.AuthenticatedPrincipal;

import com.marketplace.domain.user.User;

public class UserPrincipal implements AuthenticatedPrincipal {

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public long getUserId() {
        return user.getId();
    }

	@Override
	public String getName() {
		return user.getUid();
	}

}
