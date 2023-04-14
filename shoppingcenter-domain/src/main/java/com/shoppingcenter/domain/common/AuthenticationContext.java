package com.shoppingcenter.domain.common;

import com.shoppingcenter.domain.user.User;

public interface AuthenticationContext {

    User getCurrentUser();

    long getUserId();

    User.Role getRole();

    boolean isSiteAdmin();

}
