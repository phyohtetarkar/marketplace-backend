package com.marketplace.domain.user.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class GetUserByIdUseCase {

	@Autowired
	private UserDao dao;

	@Transactional(readOnly = true)
	public User apply(long id) {
		var user = dao.findById(id);
		if (user == null) {
			throw ApplicationException.notFound("User not found");
		}
		
		if (user.getRole() == User.Role.OWNER) {
			user.setPermissions(List.of(User.Permission.values()));
		} else {
			user.setPermissions(dao.getPermissionsByUser(id));
		}
		return user;
	}

}
