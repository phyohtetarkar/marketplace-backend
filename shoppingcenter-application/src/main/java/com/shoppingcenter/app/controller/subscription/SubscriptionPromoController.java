package com.shoppingcenter.app.controller.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.subscription.dto.SubscriptionPromoDTO;
import com.shoppingcenter.domain.subscription.SubscriptionPromoQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/subscription-promos")
@Tag(name = "SubscriptionPromo")
public class SubscriptionPromoController {

	@Autowired
	private SubscriptionPromoService subscriptionPromoService;
	
	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@PostMapping
	public SubscriptionPromoDTO create(@RequestBody SubscriptionPromoDTO dto) {
		return subscriptionPromoService.create(dto);
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@PutMapping
	public SubscriptionPromoDTO update(@RequestBody SubscriptionPromoDTO dto) {
		return subscriptionPromoService.update(dto);
	}
	
	@DeleteMapping("{id:\\d+}")
	public void delete(@PathVariable long id) {
		subscriptionPromoService.delete(id);
	}
	
	@GetMapping("{code}")
	public SubscriptionPromoDTO findByCode(@PathVariable String code) {
		return subscriptionPromoService.findByCode(code);
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@GetMapping
	public PageDataDTO<SubscriptionPromoDTO> findAll(
			@RequestParam(required = false) Boolean available,
			@RequestParam(required = false) Boolean used,
			@RequestParam(required = false) Boolean expired,
			@RequestParam(required = false) Integer page) {
		var query = SubscriptionPromoQuery.builder()
				.available(available)
				.used(used)
				.expired(expired)
				.page(page)
				.build();
		
		return subscriptionPromoService.findAll(query);
	}
	
}
