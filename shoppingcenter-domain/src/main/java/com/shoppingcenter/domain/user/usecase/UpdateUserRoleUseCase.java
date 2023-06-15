package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.user.User.Role;
import com.shoppingcenter.domain.user.UserDao;

public class UpdateUserRoleUseCase {

    private UserDao dao;

    public UpdateUserRoleUseCase(UserDao dao) {
        this.dao = dao;
    }

    public void apply(long userId, Role role) {
        if (role == null) {
    		throw new ApplicationException("Required user role");
    	}
        
		var user = dao.findById(userId);
    	
    	if (user == null) {
    		throw new ApplicationException("User not found");
    	}
    	
    	if (!user.isVerified()) {
    		throw new ApplicationException("User is not verified");
    	}
        
        dao.updateRole(userId, role);
    }
    
    public void apply(String phone, Role role) {
    	if (!Utils.hasText(phone)) {
    		throw new ApplicationException("Required phone number");
    	}
    	
    	if (role == null) {
    		throw new ApplicationException("Required user role");
    	}
    	
    	var user = dao.findByPhone(phone);
    	
    	if (user == null) {
    		throw new ApplicationException("User not found");
    	}
    	
    	if (!user.isVerified()) {
    		throw new ApplicationException("User is not verified");
    	}
    	
    	dao.updateRole(user.getId(), role);
    }

}
