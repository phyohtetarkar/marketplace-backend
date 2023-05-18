package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.common.OTPVerificationAdapter;
import com.shoppingcenter.domain.misc.CityDao;
import com.shoppingcenter.domain.misc.OTPAttemptDao;
import com.shoppingcenter.domain.misc.usecase.DeleteCityUseCase;
import com.shoppingcenter.domain.misc.usecase.GetAllCityUseCase;
import com.shoppingcenter.domain.misc.usecase.RequestOTPUseCase;
import com.shoppingcenter.domain.misc.usecase.SaveCityUseCase;
import com.shoppingcenter.domain.misc.usecase.VerifyOTPUseCase;
import com.shoppingcenter.domain.user.UserDao;

@Configuration
public class MiscModule {

	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OTPAttemptDao otpAttemptDao;
	
	@Autowired
	private OTPVerificationAdapter otpVerificationAdapter;
	
	@Bean
	SaveCityUseCase saveCityUseCase() {
		return new SaveCityUseCase(cityDao);
	}
	
	@Bean
	DeleteCityUseCase deleteCityUseCase() {
		return new DeleteCityUseCase(cityDao);
	}
	
	@Bean
	GetAllCityUseCase getAllCityUseCase() {
		return new GetAllCityUseCase(cityDao);
	}
	
	@Bean
	RequestOTPUseCase requestOTPUseCase() {
		var usecase = new RequestOTPUseCase();
		usecase.setUserDao(userDao);
		usecase.setOtpAttemptDao(otpAttemptDao);
		usecase.setOtpVerificationAdapter(otpVerificationAdapter);
		return usecase;
	}
	
	@Bean
	VerifyOTPUseCase verifyOTPUseCase() {
		return new VerifyOTPUseCase(otpVerificationAdapter);
	}
}
