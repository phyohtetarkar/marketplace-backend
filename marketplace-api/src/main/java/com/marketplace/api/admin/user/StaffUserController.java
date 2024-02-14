package com.marketplace.api.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/staff-users")
@PreAuthorize("hasAuthority('ROLE_OWNER')")
@Tag(name = "Admin")
public class StaffUserController {

	@Autowired
	private UserControllerFacade userFacade;

	@PutMapping("{userId:\\d+}/permissions")
	public void updatePermissions(@PathVariable long userId, @RequestBody List<User.Permission> body) {
		if (userId == AuthenticationUtil.getAuthenticatedUserId()) {
			throw new ApplicationException("Unable to update permissions");
		}
		userFacade.updatePermissions(userId, body);
	}

	@PutMapping("{userId:\\d+}/dismiss-admin")
	public void dismissAdmin(@PathVariable long userId) {
		if (userId == AuthenticationUtil.getAuthenticatedUserId()) {
			throw new ApplicationException("Unable to dismiss admin");
		}
		userFacade.updateRole(userId, User.Role.USER);
	}

}
