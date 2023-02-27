package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.User;

public interface UpdateUserRoleUseCase {
    void apply(String userId, User.Role role);
}
