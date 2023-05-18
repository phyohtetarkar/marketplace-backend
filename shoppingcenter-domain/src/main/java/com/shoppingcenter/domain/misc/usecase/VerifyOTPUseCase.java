package com.shoppingcenter.domain.misc.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.common.OTPVerificationAdapter;

public class VerifyOTPUseCase {

	private OTPVerificationAdapter otpVerificationAdapter;

	public VerifyOTPUseCase(OTPVerificationAdapter otpVerificationAdapter) {
		super();
		this.otpVerificationAdapter = otpVerificationAdapter;
	}

	public void apply(String code, int requestId) {
		var result = otpVerificationAdapter.verify(code, requestId);
		if (!result.isStatus()) {
			throw new ApplicationException("Invalid otp code");
		}
	}

}
