package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.PasswordEncoderAdapter;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class ChangePasswordUseCase {

	private UserDao userDao;
	
	private PasswordEncoderAdapter passwordEncoderAdapter;
	
	public void apply(long userId, String oldPassword, String newPassword) {
		var user = userDao.findById(userId);
		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		if (!passwordEncoderAdapter.matches(oldPassword, user.getPassword())) {
			throw new ApplicationException("Current password incorrect");
		}
		
		if (!Utils.hasText(newPassword) || newPassword.length() < 8) {
			throw new ApplicationException("Password must be at least 8 characters");
		}
		
		userDao.updatePassword(userId, passwordEncoderAdapter.encode(newPassword));
	}
}
