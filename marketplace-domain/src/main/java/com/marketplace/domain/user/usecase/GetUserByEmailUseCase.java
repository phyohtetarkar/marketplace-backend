package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class GetUserByEmailUseCase {

	@Autowired
	private UserDao dao;

	public User apply(String email) {
		var user = dao.findByEmail(email);
		if (user == null) {
			throw ApplicationException.notFound("User not found");
		}
		return user;
	}
}
