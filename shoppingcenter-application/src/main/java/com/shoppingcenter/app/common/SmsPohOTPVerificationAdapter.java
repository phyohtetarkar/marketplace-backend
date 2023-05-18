package com.shoppingcenter.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppingcenter.domain.common.OTPVerification;
import com.shoppingcenter.domain.common.OTPVerificationAdapter;

@Component
public class SmsPohOTPVerificationAdapter implements OTPVerificationAdapter {
	
	@Autowired
	private AppProperties properties;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public OTPVerification request(String phone) {
//		try {
//			var url = properties.getSmsPohUrl() + "/v2/request";
//			
//			var params = new HashMap<String, String>();
//			params.put("access-token", properties.getSmsPohApiKey());
//			params.put("channel", "sms");
//			params.put("to", phone);
//			params.put("brand_name", properties.getBrandName());
//			
//			var response = restTemplate.getForEntity(url, OTPVerificationDTO.class, params);
//			
//			if (response.getStatusCode().value() != 200) {
//				throw new ApplicationException("Unable to request OTP. Please try again.");
//			}
//			
//			return response.getBody().toDomain();
//		} catch (Exception e) {
//			throw new ApplicationException(e.getMessage());
//		}
		
		var mock = new OTPVerification();
		mock.setStatus(true);
		return mock;
	}

	@Override
	public OTPVerification verify(String code, int requestId) {
//		try {
//			var url = properties.getSmsPohUrl() + "/v1/verify";
//			var params = new HashMap<String, Object>();
//			params.put("access-token", properties.getSmsPohApiKey());
//			params.put("code", code);
//			params.put("request_id", requestId);
//			
//			var response = restTemplate.getForEntity(url, OTPVerificationDTO.class, params);
//			
//			return response.getBody().toDomain();
//		} catch (Exception e) {
//			throw new ApplicationException(e.getMessage());
//		}
		
		var mock = new OTPVerification();
		mock.setStatus(true);
		return mock;
	}

}
 