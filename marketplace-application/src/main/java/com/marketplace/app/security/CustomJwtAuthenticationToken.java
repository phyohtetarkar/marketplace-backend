package com.marketplace.app.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import com.marketplace.api.UserPrincipal;

public class CustomJwtAuthenticationToken extends AbstractAuthenticationToken {
	
	private final Object principal;
	
	private Jwt token;

	public CustomJwtAuthenticationToken(Jwt token, UserPrincipal principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.token = token;
		super.setAuthenticated(true);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}
	
	public final Jwt getToken() {
		return this.token;
	}

}
