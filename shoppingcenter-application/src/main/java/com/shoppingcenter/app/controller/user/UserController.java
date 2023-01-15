package com.shoppingcenter.app.controller.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.user.UserQuery;
import com.shoppingcenter.core.user.UserService;
import com.shoppingcenter.core.user.model.User;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private ModelMapper modelMapper;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@RequestBody UserDTO user, Authentication authentication) {
		user.setId(authentication.getName());
		service.create(modelMapper.map(user, User.class));
	}

	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@GetMapping
	public PageData<UserDTO> findAll(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) Integer page) {

		UserQuery query = UserQuery.builder()
				.name(name)
				.phone(phone)
				.page(page)
				.build();

		return modelMapper.map(service.findAll(query), UserDTO.pageType());
	}

}
