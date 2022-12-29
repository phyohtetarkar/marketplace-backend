package com.shoppingcenter.app.controller.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.shop.ShopQueryService;
import com.shoppingcenter.core.shop.model.Shop;
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
	private ShopQueryService shopQueryService;

	@PreAuthorize("#user.id == authentication.name")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@RequestBody User user) {
		service.create(user);
	}

	@PreAuthorize("#user.id == authentication.name")
	@PutMapping
	public void update(@RequestBody User user) {
		service.update(user);
	}

	@PreAuthorize("#id == authentication.name")
	@PostMapping(value = "{id}/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void uploadImage(@PathVariable String id, @RequestPart MultipartFile file) {
		try {
			UploadFile uploadFile = new UploadFile();
			uploadFile.setFile(file.getResource().getFile());
			uploadFile.setOriginalFileName(file.getOriginalFilename());
			uploadFile.setSize(file.getSize());
			service.uploadImage(null, uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@PreAuthorize("#id == authentication.name or hasRole('ROLE_ADMIN')")
	@GetMapping("{id}/shops")
	public PageData<Shop> getShops(
			@PathVariable String id,
			@RequestParam(required = false) Integer page) {
		return shopQueryService.findByUser(id, page);
	}

}
