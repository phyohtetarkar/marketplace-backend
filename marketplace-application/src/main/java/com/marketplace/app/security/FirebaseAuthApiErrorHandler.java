package com.marketplace.app.security;

import java.io.IOException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.domain.ApplicationException;

public class FirebaseAuthApiErrorHandler extends DefaultResponseErrorHandler {
	
	private ObjectMapper objectMapper;

	public FirebaseAuthApiErrorHandler(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}

	@Override
	protected void handleError(ClientHttpResponse response, HttpStatusCode statusCode) throws IOException {
		if  (statusCode.is4xxClientError()) {
			var json = objectMapper.readTree(response.getBody());
			throw new ApplicationException(json.get("error").get("message").asText());
		} else {
			super.handleError(response, statusCode);
		}
	}
	
}
