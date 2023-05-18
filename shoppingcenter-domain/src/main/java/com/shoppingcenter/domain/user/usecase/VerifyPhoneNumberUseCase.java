package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.user.PhoneNumberVerify;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class VerifyPhoneNumberUseCase {

	private UserDao userDao;
	
	private VerifyOTPUseCase verifyOTPUseCase;
	
	public void apply(PhoneNumberVerify data) {
		verifyOTPUseCase.apply(data.getCode(), data.getRequestId());
		
		userDao.updateVerified(data.getUserId(), true);
	}
}
