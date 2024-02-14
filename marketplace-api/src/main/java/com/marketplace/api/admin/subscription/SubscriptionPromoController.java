package com.marketplace.api.admin.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.subscription.SubscriptionPromoQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/subscription-promos")
@Tag(name = "Admin")
public class SubscriptionPromoController {

	@Autowired
	private SubscriptionPromoControllerFacade subscriptionPromoFacade;
	
	@PreAuthorize("hasPermission('PROMO_CODE', 'WRITE')")
	@PostMapping
	public SubscriptionPromoDTO create(@RequestBody SubscriptionPromoEditDTO body) {
		return subscriptionPromoFacade.create(body);
	}
	
	@PreAuthorize("hasPermission('PROMO_CODE', 'WRITE')")
	@PutMapping
	public SubscriptionPromoDTO update(@RequestBody SubscriptionPromoEditDTO body) {
		return subscriptionPromoFacade.update(body);
	}
	
	@PreAuthorize("hasPermission('PROMO_CODE', 'WRITE')")
	@DeleteMapping("{id:\\d+}")
	public void delete(@PathVariable long id) {
		subscriptionPromoFacade.delete(id);
	}
	
	@PreAuthorize("hasPermission('PROMO_CODE', 'WRITE') or hasPermission('PROMO_CODE', 'READ')")
	@GetMapping("{id:\\d+}")
	public SubscriptionPromoDTO findById(@PathVariable long id) {
		return subscriptionPromoFacade.findById(id);
	}
	
	@PreAuthorize("hasPermission('PROMO_CODE', 'WRITE') or hasPermission('PROMO_CODE', 'READ')")
	@GetMapping
	public PageDataDTO<SubscriptionPromoDTO> findAll(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) Boolean available,
			@RequestParam(required = false) Boolean used,
			@RequestParam(required = false) Boolean expired,
			@RequestParam(required = false) Integer page) {
		var query = SubscriptionPromoQuery.builder()
				.code(code)
				.available(available)
				.used(used)
				.expired(expired)
				.page(page)
				.build();
		
		return subscriptionPromoFacade.findAll(query);
	}
	
}
