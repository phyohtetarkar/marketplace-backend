package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;

public class GetUserByPhoneUseCase {

    private UserDao dao;

    public GetUserByPhoneUseCase(UserDao dao) {
        this.dao = dao;
    }

    public User apply(String phone) {
        var user = dao.findByPhone(phone);
        // if (user == null) {
        // throw new ApplicationException("User not found");
        // }
        return user;
    }

}
