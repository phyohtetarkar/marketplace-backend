package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops")
@Tag(name = "Shop")
public class ShopController {

	@Autowired
	private ShopService shopFacade;

	@GetMapping("{slug}")
	public ShopDTO findBySlug(@PathVariable String slug) {
		return shopFacade.findBySlug(slug);
	}

	@GetMapping
	public PageDataDTO<ShopDTO> findAll(
			@RequestParam(required = false) String q,
			@RequestParam(required = false) Integer page) {
		var query = ShopQuery.builder()
				.q(q)
				.status(Shop.Status.APPROVED)
				.page(page)
				.build();
		
		return shopFacade.findAll(query);
	}

}
