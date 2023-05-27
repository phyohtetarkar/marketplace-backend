package com.shoppingcenter.domain.user.usecase;

import java.time.LocalDate;
import java.time.ZoneOffset;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.misc.OTPAttemptDao;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.user.PhoneNumberUpdate;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class UpdatePhoneNumberUseCase {

	private UserDao dao;
	
	private OTPAttemptDao otpAttemptDao;
	
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
		
		var date = LocalDate.now(ZoneOffset.UTC).toString();
		var attempt = otpAttemptDao.getAttempt(phoneNumber, date);
		
		if (attempt == null) {
			throw new ApplicationException("Invalid otp code");
		}
		
		var result = verifyOTPUseCase.apply(data.getCode(), attempt.getRequestId());
		
		if (!result.isStatus()) {
			throw new ApplicationException("Invalid otp code");
		}

		dao.updatePhoneNumber(data.getUserId(), phoneNumber);
	}

}
