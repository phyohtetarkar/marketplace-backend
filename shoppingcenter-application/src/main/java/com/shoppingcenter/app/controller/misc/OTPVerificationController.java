package com.shoppingcenter.app.controller.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.misc.dto.OTPVerificationDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/otp")
@Tag(name = "OTPVerification")
public class OTPVerificationController {
	
	@Autowired
	private OTPVerificationFacade otpVerificationFacade;

	@GetMapping("request")
	public OTPVerificationDTO request(@RequestParam String phone) {
		return otpVerificationFacade.request(phone);
	}
	
//	@GetMapping("verify")
//	public void verify(@RequestParam String code, @RequestParam("request-id") int requestId) {
//		otpVerificationFacade.verify(code, requestId);
//	}
	
}
