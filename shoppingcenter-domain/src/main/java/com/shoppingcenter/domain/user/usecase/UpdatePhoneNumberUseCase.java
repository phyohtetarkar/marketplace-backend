package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.user.UserDao;

public class UpdatePhoneNumberUseCase {

	private UserDao dao;

	public UpdatePhoneNumberUseCase(UserDao dao) {
		super();
		this.dao = dao;
	}

	public void apply(long userId, String phoneNumber) {
		if (!dao.existsById(userId)) {
			throw new ApplicationException("User not found");
		}

		var phoneRegex = "^(09)\\d{7,12}$";

		if (!Utils.hasText(phoneNumber) || !phoneNumber.matches(phoneRegex)) {
			throw new ApplicationException("Required valid phone number");
		}

		dao.updatePhoneNumber(userId, phoneNumber);
	}

}
