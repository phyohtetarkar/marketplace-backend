package com.shoppingcenter.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.shoppingcenter.domain.common.PasswordEncoderAdapter;

@Component
public class DefaultPasswordEncoderAdapter implements PasswordEncoderAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		if (!StringUtils.hasText(rawPassword) || !StringUtils.hasText(encodedPassword)) {
			return false;
		}
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
