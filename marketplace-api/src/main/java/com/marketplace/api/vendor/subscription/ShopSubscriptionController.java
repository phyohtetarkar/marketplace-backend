package com.marketplace.api.vendor.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.domain.payment.PaymentTokenResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/vendor/shops/{shopId:\\d+}")
@PreAuthorize("@authz.isShopMember(#shopId)")
@Tag(name = "Vendor")
public class ShopSubscriptionController {
	
	@Autowired
	private ShopSubscriptionControllerFacade shopSubscriptionFacade;

	@PostMapping("renew-subscription")
	public PaymentTokenResponse renewSubscription(
			@PathVariable long shopId, 
			@RequestBody RenewSubscriptionDTO body) {
		body.setShopId(shopId);
		return shopSubscriptionFacade.renewSubscription(body);
	}
	
	@GetMapping("subscriptions")
	public List<ShopSubscriptionDTO> getActiveSubscriptions(@PathVariable long shopId) {
		return shopSubscriptionFacade.getActiveShopSubscriptions(shopId);
	}
	
	@GetMapping("subscriptions/{invoiceNo:\\d+}")
	public ShopSubscriptionDTO getShopSubscriptions(
			@PathVariable long shopId, 
			@PathVariable long invoiceNo) {
		return shopSubscriptionFacade.getShopSubscription(invoiceNo);
	}
	
}
