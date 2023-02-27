package com.shoppingcenter.domain.common;

import com.shoppingcenter.domain.user.User;

public interface AuthenticationContext {

    User getCurrentUser();

    String getUserId();

    User.Role getRole();

    boolean isSiteAdmin();

}
