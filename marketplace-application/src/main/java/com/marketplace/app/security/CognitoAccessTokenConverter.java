package com.marketplace.app.security;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

@SuppressWarnings("unused")
@Deprecated
public class CognitoAccessTokenConverter implements Converter<Map<String, Object>, Map<String, Object>> {

	private static final String COGNITO_GROUPS = "cognito:groups";
	private static final String SPRING_AUTHORITIES = "authorities";
	private static final String COGNITO_USERNAME = "username";
	private static final String SPRING_USER_NAME = "user_name";

	//private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

	@Override
	public Map<String, Object> convert(Map<String, Object> claims) {
//		Map<String, Object> convertedClaims = this.delegate.convert(claims);
//
//		if (claims.containsKey(COGNITO_GROUPS)) {
//			convertedClaims.put(SPRING_AUTHORITIES, claims.get(COGNITO_GROUPS));
//		}
//
//		if (claims.containsKey(COGNITO_USERNAME)) {
//			convertedClaims.put(SPRING_USER_NAME, claims.get(COGNITO_USERNAME));
//		}

		return null;
	}

}
