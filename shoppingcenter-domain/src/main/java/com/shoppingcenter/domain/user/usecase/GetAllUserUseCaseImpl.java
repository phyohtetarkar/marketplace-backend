package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserDao;
import com.shoppingcenter.domain.user.UserQuery;

public class GetAllUserUseCaseImpl implements GetAllUserUseCase {

    private UserDao dao;

    public GetAllUserUseCaseImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public PageData<User> apply(UserQuery query) {
        return dao.findAll(query);
    }

}
