package com.shoppingcenter.app.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String greeting(
			@RequestPart("body") Test test,
			@RequestPart("files") MultipartFile[] files,
			Authentication authentication) {
		System.out.println(test.getName());
		System.out.println(files.length);
		// String username = Optional.ofNullable(SecurityContextHolder.getContext())
		// .map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated).map(a
		// -> a.getName())
		// .orElse("Unknown");
		return "Hello, " + authentication.getName();
	}
}
