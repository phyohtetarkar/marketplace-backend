package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.user.UserDao;

public class CheckUserExistsByPhoneUseCase {

	public UserDao dao;

	public CheckUserExistsByPhoneUseCase(UserDao dao) {
		super();
		this.dao = dao;
	}

	public boolean apply(String phone) {
		if (phone == null) {
			return false;
		}
		return dao.existsByPhone(phone);
	}
}
