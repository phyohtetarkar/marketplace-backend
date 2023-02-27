package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.user.User;
import com.shoppingcenter.domain.user.UserQuery;

public interface GetAllUserUseCase {

    PageData<User> apply(UserQuery query);

}
