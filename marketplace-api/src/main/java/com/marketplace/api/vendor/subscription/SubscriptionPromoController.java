package com.marketplace.api.vendor.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.vendor.VendorDataMapper;
import com.marketplace.domain.subscription.usecase.GetSubscriptionPromoByCodeUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/vendor/subscription-promos")
@Tag(name = "Vendor")
public class SubscriptionPromoController {

	@Autowired
	private GetSubscriptionPromoByCodeUseCase getSubscriptionPromoByCodeUseCase;
	
	@Autowired
	private VendorDataMapper mapper;
	
	@GetMapping("{code}")
	public SubscriptionPromoDTO findByCode(@PathVariable String code) {
		var source = getSubscriptionPromoByCodeUseCase.apply(code);
		return mapper.map(source);
		
	}
}
