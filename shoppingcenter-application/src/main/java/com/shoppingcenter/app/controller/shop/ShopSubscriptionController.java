package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.shop.dto.RenewSubscriptionDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSubscriptionDTO;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;
import com.shoppingcenter.domain.subscription.ShopSubscription;
import com.shoppingcenter.domain.subscription.ShopSubscriptionQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-subscriptions")
@Tag(name = "ShopSubscription")
public class ShopSubscriptionController {

	@Autowired
	private ShopSubscriptionService shopSubscriptionService;

	@Autowired
	private AuthenticationContext authentication;

	@PostMapping("{shopId:\\d+}/renew-subscription")
	public PaymentTokenResponse renewSubscription(@PathVariable long shopId, @RequestBody RenewSubscriptionDTO data) {
		data.setShopId(shopId);
		data.setUserId(authentication.getUserId());
		return shopSubscriptionService.renewSubscription(data);
	}

	@GetMapping("{shopId:\\d+}/current-subscription")
	public ShopSubscriptionDTO getCurrentSubscription(@PathVariable long shopId) {
		return shopSubscriptionService.getCurrentSubscription(shopId);
	}

	@GetMapping("{shopId:\\d+}/pre-subscriptions")
	public List<ShopSubscriptionDTO> getPreSubscriptions(@PathVariable long shopId) {
		return shopSubscriptionService.getPreSubscriptions(shopId);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SUBSCRIPTION_HISTORY_READ')")
	@GetMapping
	public PageDataDTO<ShopSubscriptionDTO> findAll(
			@RequestParam(required = false, name = "shop-id") Long shopId,
			@RequestParam(required = false, name = "from-date") String fromDate,
			@RequestParam(required = false, name = "to-date") String toDate,
			@RequestParam(required = false, name = "time-zone") String timeZone,
			@RequestParam(required = false) ShopSubscription.Status status,
			@RequestParam(required = false) Integer page) {
		
		var query = ShopSubscriptionQuery.builder()
				.shopId(shopId)
				.fromDate(fromDate)
				.toDate(toDate)
				.status(status)
				.timeZone(timeZone)
				.page(page)
				.build();

		return shopSubscriptionService.findAllShopSubscriptions(query);
	}

}
