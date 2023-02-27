package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.User.Role;

public class UpdateUserRoleUseCaseImpl implements UpdateUserRoleUseCase {

    private UserDao dao;

    public UpdateUserRoleUseCaseImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(String userId, Role role) {
        if (!Utils.hasText(userId) || !dao.existsById(userId)) {
            throw new ApplicationException("User not found");
        }
        dao.updateRole(userId, role);
    }

}
