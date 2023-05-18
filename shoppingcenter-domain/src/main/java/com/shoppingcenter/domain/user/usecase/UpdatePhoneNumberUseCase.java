package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.user.PhoneNumberUpdate;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class UpdatePhoneNumberUseCase {

	private UserDao dao;
	
	private VerifyOTPUseCase verifyOTPUseCase;

	public void apply(PhoneNumberUpdate data) {
		if (!dao.existsById(data.getUserId())) {
			throw new ApplicationException("User not found");
		}

		var phoneRegex = "^(09)\\d{7,12}$";
		
		var phoneNumber = data.getPhone();

		if (!Utils.hasText(phoneNumber) || !phoneNumber.matches(phoneRegex)) {
			throw new ApplicationException("Phone number not valid");
		}
		
		verifyOTPUseCase.apply(data.getCode(), data.getRequestId());

		dao.updatePhoneNumber(data.getUserId(), phoneNumber);
	}

}
