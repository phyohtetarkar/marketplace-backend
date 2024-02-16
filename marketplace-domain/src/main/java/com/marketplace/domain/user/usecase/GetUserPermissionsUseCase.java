package com.marketplace.domain.user.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class GetUserPermissionsUseCase {

	@Autowired
	private UserDao dao;

	public List<User.Permission> apply(long id) {
		if (!dao.existsById(id)) {
			throw ApplicationException.notFound("User not found");
		}
		
		var role = dao.getRole(id);
		
		if (role == User.Role.OWNER) {
			return List.of(User.Permission.values());
		}
		
		return dao.getPermissionsByUser(id);
	}
}
