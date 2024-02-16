package com.marketplace.app.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.domain.user.dao.UserDao;

public class CustomPermissionEvaluator implements PermissionEvaluator {

	private UserDao userDao;

	public CustomPermissionEvaluator(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (authentication == null || targetDomainObject == null || !(permission instanceof String)) {
			return false;
		}
		var p = targetDomainObject.toString().toUpperCase() + "_" + permission.toString().toUpperCase();
		return hasPrivilege(authentication, p);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(authentication, permission.toString().toUpperCase());
	}

	private boolean hasPrivilege(Authentication auth, String permission) {
		for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
			if (grantedAuth.getAuthority().contentEquals(permission)) {
				return true;
			}
		}
		return userDao.getUserPermission(AuthenticationUtil.getAuthenticatedUserId(), permission) != null;
	}

}
