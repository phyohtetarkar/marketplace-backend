package com.shoppingcenter.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.user.UserQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User")
public class UserController {

	@Autowired
	private UserFacade userFacade;

	@Value("${app.security.api-key}")
	private String apiKey;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@RequestParam("api-key") String key, @RequestBody UserEditDTO user) {
		if (!apiKey.equals(key)) {
			throw new ApplicationException("Invalid api key");
		}
		userFacade.create(user);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@GetMapping("{id}")
	public UserDTO getLoginUser(@PathVariable String id) {
		return userFacade.findById(id);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@GetMapping
	public PageData<UserDTO> findAll(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) Integer page) {

		var query = UserQuery.builder()
				.name(name)
				.phone(phone)
				.page(page)
				.build();

		return userFacade.findAll(query);
	}

}
