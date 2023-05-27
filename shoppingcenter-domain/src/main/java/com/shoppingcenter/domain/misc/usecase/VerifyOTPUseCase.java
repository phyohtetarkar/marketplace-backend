package com.shoppingcenter.domain.misc.usecase;

import com.shoppingcenter.domain.common.OTPVerification;
import com.shoppingcenter.domain.common.OTPVerificationAdapter;

public class VerifyOTPUseCase {

	private OTPVerificationAdapter otpVerificationAdapter;

	public VerifyOTPUseCase(OTPVerificationAdapter otpVerificationAdapter) {
		super();
		this.otpVerificationAdapter = otpVerificationAdapter;
	}

	public OTPVerification apply(String code, int requestId) {
		var result = otpVerificationAdapter.verify(code, requestId);
		return result;
	}

}
