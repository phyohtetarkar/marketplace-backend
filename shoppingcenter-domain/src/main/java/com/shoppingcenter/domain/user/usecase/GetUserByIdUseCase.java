package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;

public class GetUserByIdUseCase {

    private UserDao dao;

    public GetUserByIdUseCase(UserDao dao) {
        this.dao = dao;
    }

    public User apply(long id) {
        User user = dao.findById(id);
        // if (user == null) {
        // throw new ApplicationException(ErrorCodes.NOT_FOUND, "User not found");
        // }
        return user;
    }

}
