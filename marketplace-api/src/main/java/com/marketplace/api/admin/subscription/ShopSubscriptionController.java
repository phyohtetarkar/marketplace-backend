package com.marketplace.api.admin.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.ShopSubscriptionQuery;
import com.marketplace.domain.subscription.usecase.GetAllShopSubscriptionUseCase;
import com.marketplace.domain.subscription.usecase.GetShopSubscriptionTransactionUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/shop-subscriptions")
@Tag(name = "Admin")
public class ShopSubscriptionController {

	@Autowired
	private GetAllShopSubscriptionUseCase getAllShopSubscriptionUseCase;
	
	@Autowired
	private GetShopSubscriptionTransactionUseCase getShopSubscriptionTransactionUseCase;
	
	@Autowired
    private AdminDataMapper mapper;
	
	@PreAuthorize("hasPermission('SUBSCRIPTION_HISTORY', 'READ')")
	@GetMapping("{invoiceNo:\\d+}/transaction")
	public ShopSubscriptionTransactionDTO getTransaction(@PathVariable long invoiceNo) {
		var source = getShopSubscriptionTransactionUseCase.apply(invoiceNo);
		return mapper.map(source);
	}
	
	@PreAuthorize("hasPermission('SUBSCRIPTION_HISTORY', 'READ')")
	@GetMapping
	public PageDataDTO<ShopSubscriptionDTO> getAll(
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
		var source = getAllShopSubscriptionUseCase.apply(query);
		
		return mapper.mapShopSubscriptionPage(source);
	}
	
}
