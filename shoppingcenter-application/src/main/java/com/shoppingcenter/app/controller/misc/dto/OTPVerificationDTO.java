package com.shoppingcenter.app.controller.misc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoppingcenter.domain.common.OTPVerification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPVerificationDTO {

	private boolean status;
	
	@JsonProperty("request_id")
	private int requestId;
	
	public OTPVerification toDomain() {
		var data = new OTPVerification();
		data.setStatus(status);
		data.setRequestId(requestId);
		return data;
	}
	
}
