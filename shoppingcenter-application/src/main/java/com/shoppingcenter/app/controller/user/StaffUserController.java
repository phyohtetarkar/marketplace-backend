package com.shoppingcenter.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.user.dto.AddStaffUserDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/staff-users")
@Tag(name = "StaffUser")
public class StaffUserController {

	@Autowired
	private UserFacade userFacade;
	
	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@PostMapping
	public void add(@RequestBody AddStaffUserDTO dto) {
		userFacade.updateRole(dto.getPhone(), dto.getRole());
	}
}
