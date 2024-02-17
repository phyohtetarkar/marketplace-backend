package com.marketplace.api.vendor.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/vendor/shops")
@Tag(name = "Vendor")
public class ShopCreateController {

	@Autowired
	private ShopControllerFacade shopFacade;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@ModelAttribute ShopCreateDTO body) {
		body.setUserId(AuthenticationUtil.getAuthenticatedUserId());
		shopFacade.create(body);
	}
}
