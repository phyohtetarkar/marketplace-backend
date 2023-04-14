package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;

public class GetUserByPhoneUseCaseImpl implements GetUserByPhoneUseCase {

    private UserDao dao;

    public GetUserByPhoneUseCaseImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User apply(String phone) {
        var user = dao.findByPhone(phone);
        // if (user == null) {
        // throw new ApplicationException("User not found");
        // }
        return user;
    }

}
