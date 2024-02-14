package com.marketplace.api.consumer.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.vendor.shop.ShopAcceptedPaymentDTO;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/shops")
@Tag(name = "Consumer")
public class ShopController {

	@Autowired
	private ShopControllerFacade shopFacade;
	
	@GetMapping("{slug}")
	public ShopDTO findBySlug(@PathVariable String slug) {
		return shopFacade.findBySlug(slug);
	}
	
	@GetMapping("{shopId:\\d+}/setting")
	public ShopSettingDTO getSetting(@PathVariable long shopId) {
		return shopFacade.getShopSetting(shopId);
	}
	
	@GetMapping("{shopId:\\d+}/accepted-payments")
    public List<ShopAcceptedPaymentDTO> findAcceptedPayments(@PathVariable long shopId) {
        return shopFacade.findAcceptedPaymentsByShop(shopId);
    }

	@GetMapping
	public PageDataDTO<ShopDTO> findAll(
			@RequestParam(required = false) String q,
			@RequestParam(required = false, name = "city-id") Long cityId,
			@RequestParam(required = false) Integer page) {
		var query = ShopQuery.builder()
				.q(q)
				.cityId(cityId)
				.status(Shop.Status.APPROVED)
				.expired(false)
				.page(page)
				.build();
		
		return shopFacade.findAll(query);
	}
	
}
