package com.marketplace.domain.user.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class UpdateUserPermissionsUseCase {
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public void apply(long userId, List<User.Permission> permissions) {
		var role = userDao.getRole(userId);
		if (role == null) {
			throw new ApplicationContextException("User not found");
		}
		
		if (role == User.Role.USER) {
			throw new ApplicationContextException("Unable to update permissions");
		}
		
		if (role == User.Role.OWNER) {
			throw new ApplicationContextException("Unable to update permissions");
		}
		
		userDao.deletePermissionsByUser(userId);
		
		userDao.updatePermissions(userId, permissions);
	}
	
}
