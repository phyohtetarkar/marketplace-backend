package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.User;

public interface GetUserByIdUseCase {

    User apply(long id);

}
