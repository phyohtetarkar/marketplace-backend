package com.shoppingcenter.domain.misc.usecase;

import java.time.LocalDate;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.OTPVerification;
import com.shoppingcenter.domain.common.OTPVerificationAdapter;
import com.shoppingcenter.domain.misc.OTPAttempt;
import com.shoppingcenter.domain.misc.OTPAttemptDao;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class RequestOTPUseCase {

	private OTPAttemptDao otpAttemptDao;
	
	private OTPVerificationAdapter otpVerificationAdapter;
	
	private UserDao userDao;
	
	public OTPVerification apply(String phone) {
		var phoneRegex = "^(09)\\d{7,12}$";
		
		if (!Utils.hasText(phone) || !phone.matches(phoneRegex)) {
			throw new ApplicationException("Required valid phone number");
		}
		
		if (!userDao.existsByPhone(phone)) {
			throw new ApplicationException("Account does not exists");
		}
		
		var date = LocalDate.now(ZoneOffset.UTC).toString();
		
		var attempt = otpAttemptDao.getAttempt(phone, date);
		
		if (attempt == null) {
			attempt = new OTPAttempt();
			attempt.setPhone(phone);
			attempt.setDate(date);
		}
		
		if (attempt.getAttempt() >= 8) {
			throw new ApplicationException("OTP request limit exceeds. Please try again later");
		}
		
		attempt.setAttempt(attempt.getAttempt() + 1);
		
		var result = otpVerificationAdapter.request(phone);
		
		if (result.isStatus()) {
			attempt.setRequestId(result.getRequestId());
			otpAttemptDao.save(attempt);
		}
		
		return result;
		
	}
	
}
