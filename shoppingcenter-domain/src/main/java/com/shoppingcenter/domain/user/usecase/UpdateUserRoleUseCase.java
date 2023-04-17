package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.user.User.Role;
import com.shoppingcenter.domain.user.UserDao;

public class UpdateUserRoleUseCase {

    private UserDao dao;

    public UpdateUserRoleUseCase(UserDao dao) {
        this.dao = dao;
    }

    public void apply(long userId, Role role) {
        if (!dao.existsById(userId)) {
            throw new ApplicationException("User not found");
        }
        dao.updateRole(userId, role);
    }

}
