package com.marketplace.api.payment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.domain.subscription.usecase.CompleteShopSubscriptionUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/payment")
@Tag(name = "Payment")
public class PaymentController {
	
	@Autowired
	private CompleteShopSubscriptionUseCase completeShopSubscriptionUseCase;
	
	/**
	 * 
	 * Server-to-server API for 2c2p payment provider
	 * 
	 * @param body
	 */
	@PostMapping("notify")
	public void acceptPaymentResult(@RequestBody Map<String, String> body) {
		try {
			if (body != null && body.get("payload") != null) {
				completeShopSubscriptionUseCase.apply(body.get("payload"));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	} 
	
}
