package com.marketplace.app.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;

import com.marketplace.api.UserPrincipal;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private UserDao userDao;
	
	private FirebaseUserAdapter firebaseUserAdapter;

	public CustomJwtAuthenticationConverter(UserDao userDao, FirebaseUserAdapter firebaseUserAdapter) {
		this.userDao = userDao;
		this.firebaseUserAdapter = firebaseUserAdapter;
	}

	@Override
	public AbstractAuthenticationToken convert(Jwt source) {
		var user = userDao.findByUid(source.getSubject());
		if (user == null) {
			
			// For first time user creation (synchronize with authentication provider)
			var authUser = firebaseUserAdapter.getUserData(source.getTokenValue());
			
			if (authUser == null) {
				throw new UsernameNotFoundException("User not found");
			}
			
			var u = new User();
			u.setUid(authUser.getUid());
			u.setName(authUser.getName());
			u.setEmail(authUser.getEmail());
			u.setImage(authUser.getImageUrl());
			u.setRole(User.Role.USER);
			
			user = userDao.create(u);
		}
		
		var principal = new UserPrincipal(user);
		
		var authorities = AuthorityUtils.createAuthorityList("ROLE_" + user.getRole().name());
		
		if (user.getRole() == User.Role.OWNER) {
			var permissions = User.Permission.values();
			for (var up : permissions) {
        		authorities.add(new SimpleGrantedAuthority(up.name()));
        	}
		} 
        
		return new CustomJwtAuthenticationToken(source, principal, authorities);
	}

}
