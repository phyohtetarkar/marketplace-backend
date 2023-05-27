package com.shoppingcenter.app.controller.misc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.misc.dto.OTPVerificationDTO;
import com.shoppingcenter.domain.misc.usecase.RequestOTPUseCase;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;

@Facade
public class OTPVerificationFacade {
	
	@Autowired
	private RequestOTPUseCase requestOTPUseCase;

	@Autowired
	private VerifyOTPUseCase verifyOTPUseCase;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public OTPVerificationDTO request(String phone) {
		var source = requestOTPUseCase.apply(phone);
		return modelMapper.map(source, OTPVerificationDTO.class);
	}
	
	public OTPVerificationDTO verify(String code, int requestId) {
		var source = verifyOTPUseCase.apply(code, requestId);
		return modelMapper.map(source, OTPVerificationDTO.class);
	}
	
}
