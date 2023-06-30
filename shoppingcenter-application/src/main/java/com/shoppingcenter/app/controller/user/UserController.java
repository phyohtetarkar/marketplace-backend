package com.shoppingcenter.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.user.dto.UpdateStaffUserDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.domain.user.UserQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User")
public class UserController {

	@Autowired
	private UserService userService;

	// @Value("${app.security.api-key}")
	// private String apiKey;

	// @ResponseStatus(HttpStatus.CREATED)
	// @PostMapping
	// public void create(@RequestParam("api-key") String key, @RequestBody
	// UserEditDTO user) {
	// if (!properties.getApiKey().equals(key)) {
	// throw new ApplicationException("Invalid api key");
	// }
	// userFacade.create(user);
	// }

	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'USER_WRITE')")
	@PostMapping("{id:\\d+}/verify")
	public void verifyUser(@PathVariable long id) {
		userService.verifyUser(id);
	}
	
//	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
//	@PutMapping("{id:\\d+}/role")
//	public void updateRole(@PathVariable long id, @RequestParam User.Role role) {
//		userService.updateRole(id, role);
//	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'STAFF_WRITE')")
	@PostMapping("{phone}/staff")
	public void addStaffUser(@PathVariable String phone, @RequestBody UpdateStaffUserDTO dto) {
		userService.addStaffUser(dto);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'STAFF_WRITE')")
	@PutMapping("{phone}/staff")
	public void updateStaffUser(@PathVariable String phone, @RequestBody UpdateStaffUserDTO dto) {
		userService.updateStaffUser(dto);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'STAFF_WRITE')")
	@DeleteMapping("{phone}/staff")
	public void removeStaffUser(@PathVariable String phone) {
		userService.removeStaffUser(phone);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@GetMapping("{id:\\d+}")
	public UserDTO getUser(@PathVariable long id) {
		return userService.findById(id);
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@GetMapping
	public PageDataDTO<UserDTO> findAll(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false, name = "staff-only") Boolean staffOnly,
			@RequestParam(required = false) Boolean verified,
			@RequestParam(required = false) Integer page) {

		var query = UserQuery.builder()
				.name(name)
				.phone(phone)
				.staffOnly(staffOnly)
				.verified(verified)
				.page(page)
				.build();

		return userService.findAll(query);
	}

}
