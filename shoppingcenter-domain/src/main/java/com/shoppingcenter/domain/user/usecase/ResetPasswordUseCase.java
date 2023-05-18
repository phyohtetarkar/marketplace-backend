package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.PasswordEncoderAdapter;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.user.PasswordReset;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class ResetPasswordUseCase {
	
	private UserDao dao;

	private VerifyOTPUseCase verifyOTPUseCase;
	
	private PasswordEncoderAdapter passwordEncoderAdapter;

	public void apply(PasswordReset data) {
		var phoneRegex = "^(09)\\d{7,12}$";
		
		var phoneNumber = data.getPhone();

		if (!Utils.hasText(phoneNumber) || !phoneNumber.matches(phoneRegex)) {
			throw new ApplicationException("Phone number not valid");
		}
		
		var user = dao.findByPhone(data.getPhone());
		
		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		verifyOTPUseCase.apply(data.getCode(), data.getRequestId());
		
		dao.updatePassword(user.getId(), passwordEncoderAdapter.encode(data.getPassword()));
	}
	
}
