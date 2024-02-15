package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User.Role;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class UpdateUserRoleUseCase {

	@Autowired
    private UserDao dao;

	@Transactional
    public void apply(long userId, Role role) {
        if (role == null) {
    		throw new ApplicationException("Required user role");
    	}
        
		var user = dao.findById(userId);
    	
    	if (user == null) {
    		throw new ApplicationException("User not found");
    	}
    	
    	dao.deletePermissionsByUser(userId);
        
        dao.updateRole(userId, role);
    }

}
