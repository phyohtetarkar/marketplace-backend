package com.marketplace.api.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.UserQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/users")
@Tag(name = "Admin")
public class UserController {

	@Autowired
	private UserControllerFacade userFacade;
	
	@PreAuthorize("hasAuthority('ROLE_OWNER')")
	@PutMapping("{userId:\\d+}/grant-admin")
	public void grantAdmin(@PathVariable long userId) {
		if (userId == AuthenticationUtil.getAuthenticatedUserId()) {
			throw new ApplicationException("Unable to grant admin");
		}
		userFacade.updateRole(userId, User.Role.ADMIN);
	}

	@PreAuthorize("hasPermission('USER', 'READ')")
	@GetMapping("{userId:\\d+}")
	public UserDTO getUser(@PathVariable long userId) {
		return userFacade.findById(userId);
	}
	
	@PreAuthorize("hasPermission('USER', 'READ')")
	@GetMapping
	public PageDataDTO<UserDTO> findAll(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) Boolean verified,
			@RequestParam(required = false, name = "staff-only") Boolean staffOnly,
			@RequestParam(required = false) Integer page) {

		var query = UserQuery.builder()
				.name(name)
				.phone(phone)
				.email(email)
				.staffOnly(staffOnly)
				.verified(verified)
				.page(page)
				.build();

		return userFacade.findAll(query);
	}

}
