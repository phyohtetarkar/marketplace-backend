package com.shoppingcenter.app.controller.user;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User")
public class UserController {

	@PostMapping("new")
	public void create(Authentication authentication) {
		
	}
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public void uploadImage(@RequestPart MultipartFile file, Authentication authentication) {
		
	}
	
}
