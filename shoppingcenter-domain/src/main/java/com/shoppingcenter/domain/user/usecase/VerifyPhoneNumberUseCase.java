package com.shoppingcenter.domain.user.usecase;

import java.time.LocalDate;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.misc.OTPAttemptDao;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.user.PhoneNumberVerify;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class VerifyPhoneNumberUseCase {

	private UserDao userDao;
	
	private OTPAttemptDao otpAttemptDao;
	
	private VerifyOTPUseCase verifyOTPUseCase;
	
	public void apply(PhoneNumberVerify data) {
		var date = LocalDate.now(ZoneOffset.UTC).toString();
		var user = userDao.findById(data.getUserId());
		
		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		var attempt = otpAttemptDao.getAttempt(user.getPhone(), date);
		
		if (attempt == null) {
			throw new ApplicationException("Invalid otp code");
		}
		
		var result = verifyOTPUseCase.apply(data.getCode(), attempt.getRequestId());
		
		if (!result.isStatus()) {
			throw new ApplicationException("Invalid otp code");
		}
		
		userDao.updateVerified(data.getUserId(), true);
	}
}
