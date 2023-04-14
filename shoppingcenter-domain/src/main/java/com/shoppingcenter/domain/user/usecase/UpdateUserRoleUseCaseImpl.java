package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.user.User.Role;
import com.shoppingcenter.domain.user.UserDao;

public class UpdateUserRoleUseCaseImpl implements UpdateUserRoleUseCase {

    private UserDao dao;

    public UpdateUserRoleUseCaseImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(long userId, Role role) {
        if (!dao.existsById(userId)) {
            throw new ApplicationException("User not found");
        }
        dao.updateRole(userId, role);
    }

}
