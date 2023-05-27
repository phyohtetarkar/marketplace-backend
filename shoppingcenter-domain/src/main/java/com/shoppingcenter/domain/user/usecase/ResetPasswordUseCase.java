package com.shoppingcenter.domain.user.usecase;

import java.time.LocalDate;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.PasswordEncoderAdapter;
import com.shoppingcenter.domain.misc.OTPAttemptDao;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.user.PasswordReset;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class ResetPasswordUseCase {
	
	private UserDao dao;
	
	private OTPAttemptDao otpAttemptDao;

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
		
		var date = LocalDate.now(ZoneOffset.UTC).toString();
		var attempt = otpAttemptDao.getAttempt(phoneNumber, date);
		
		if (attempt == null) {
			throw new ApplicationException("Invalid otp code");
		}
		
		var result = verifyOTPUseCase.apply(data.getCode(), attempt.getRequestId());
		
		if (!result.isStatus()) {
			throw new ApplicationException("Invalid otp code");
		}
		
		dao.updatePassword(user.getId(), passwordEncoderAdapter.encode(data.getPassword()));
	}
	
}
