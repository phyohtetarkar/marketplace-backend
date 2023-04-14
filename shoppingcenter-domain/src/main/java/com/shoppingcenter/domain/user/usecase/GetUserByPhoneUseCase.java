package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.User;

public interface GetUserByPhoneUseCase {

    User apply(String phone);

}
