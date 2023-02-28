package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private UserDao dao;

    public CreateUserUseCaseImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(User user) {
        if (!Utils.hasText(user.getName())) {
            throw new ApplicationException("Required user name");
        }

        var phoneRegex = "^(09)\\d{7,12}$";

        if (!Utils.hasText(user.getPhone()) || !user.getPhone().matches(phoneRegex)) {
            throw new ApplicationException("Required valid phone number");
        }

        if (dao.existsByPhone(user.getPhone())) {
            throw new ApplicationException("Phone number already in use");
        }

        if (dao.existsById(user.getId())) {
            throw new ApplicationException("User already created");
        }

        user.setRole(User.Role.USER);

        dao.create(user);
    }

}
