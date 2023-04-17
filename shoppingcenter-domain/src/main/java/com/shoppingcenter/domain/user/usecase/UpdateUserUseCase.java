package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;

public class UpdateUserUseCase {

    private UserDao dao;

    public UpdateUserUseCase(UserDao dao) {
        this.dao = dao;
    }

    public void apply(User user) {
        if (!dao.existsById(user.getId())) {
            throw new ApplicationException("User not found");
        }

        if (!Utils.hasText(user.getName())) {
            throw new ApplicationException("Required user name");
        }

        dao.update(user.getId(), user.getName(), user.getEmail());
    }

}
