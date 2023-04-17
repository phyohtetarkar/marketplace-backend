package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.UserQuery;

public class GetAllUserUseCase {

    private UserDao dao;

    public GetAllUserUseCase(UserDao dao) {
        this.dao = dao;
    }

    public PageData<User> apply(UserQuery query) {
        return dao.findAll(query);
    }

}
