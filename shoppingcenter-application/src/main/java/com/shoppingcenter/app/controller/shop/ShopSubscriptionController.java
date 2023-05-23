package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shop.dto.RenewSubscriptionDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSubscriptionDTO;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-subscriptions/{shopId:\\d+}")
@Tag(name = "ShopSubscription")
public class ShopSubscriptionController {

	@Autowired
	private ShopSubscriptionService shopSubscriptionService;
	
	@Autowired
	private AuthenticationContext authentication;
	
	@GetMapping("current-subscription")
	public ShopSubscriptionDTO getCurrentSubscription(@PathVariable long shopId) {
		return shopSubscriptionService.getCurrentSubscription(shopId);
	}
	
	@GetMapping("pre-subscriptions")
	public List<ShopSubscriptionDTO> getPreSubscriptions(@PathVariable long shopId) {
		return shopSubscriptionService.getPreSubscriptions(shopId);
	}
	
	@PostMapping("renew-subscription")
	public PaymentTokenResponse renewSubscription(@PathVariable long shopId, @RequestBody RenewSubscriptionDTO data) {
		data.setShopId(shopId);
		data.setUserId(authentication.getUserId());
		return shopSubscriptionService.renewSubscription(data);
	}
	
}
