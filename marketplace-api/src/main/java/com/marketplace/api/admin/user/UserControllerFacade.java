package com.marketplace.api.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.UserQuery;
import com.marketplace.domain.user.usecase.GetAllUserUseCase;
import com.marketplace.domain.user.usecase.GetUserByIdUseCase;
import com.marketplace.domain.user.usecase.UpdateUserPermissionsUseCase;
import com.marketplace.domain.user.usecase.UpdateUserRoleUseCase;

@Component
public class UserControllerFacade {

	@Autowired
	private UpdateUserRoleUseCase updateUserRoleUseCase;
	
	@Autowired
	private UpdateUserPermissionsUseCase updateUserPermissionsUseCase;
	
	@Autowired
	private GetUserByIdUseCase getUserByIdUseCase;
	
	@Autowired
	private GetAllUserUseCase getAllUserUseCase;
	
	@Autowired
    private AdminDataMapper mapper;
	
	public void updateRole(long userId, User.Role role) {
		updateUserRoleUseCase.apply(userId, role);
	}
	
	public void updatePermissions(long userId, List<User.Permission> values) {
		updateUserPermissionsUseCase.apply(userId, values);
	}
	
	public UserDTO findById(long userId) {
		var source = getUserByIdUseCase.apply(userId);
		return mapper.map(source);
	}
	
	public PageDataDTO<UserDTO> findAll(UserQuery query) {
        return mapper.mapUserPage(getAllUserUseCase.apply(query));
    }
	
}
