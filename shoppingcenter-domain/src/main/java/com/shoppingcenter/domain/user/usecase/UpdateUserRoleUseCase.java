package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.User;

public interface UpdateUserRoleUseCase {
    void apply(long userId, User.Role role);
}
