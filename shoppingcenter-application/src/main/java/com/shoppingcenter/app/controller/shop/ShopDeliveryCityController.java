package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.misc.dto.CityDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/delivery-cities")
@Tag(name = "ShopDeliveryCity")
public class ShopDeliveryCityController {

	@Autowired
	private ShopDeliveryCityFacade facade;
	
	@PostMapping
	public void save(@RequestBody List<CityDTO> cities) {
		//facade.save(shopId, cities);
	}
	
	@GetMapping
	public List<CityDTO> findAll(@RequestParam("shop-id") long shopId) {
		return facade.findByShop(shopId);
	}
}
