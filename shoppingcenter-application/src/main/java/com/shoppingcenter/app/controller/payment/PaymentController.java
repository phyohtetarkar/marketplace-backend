package com.shoppingcenter.app.controller.payment;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shop.ShopSubscriptionService;
import com.shoppingcenter.app.controller.shop.dto.ShopSubscriptionDTO;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ShopSubscriptionService shopSubscriptionService;
	
	@PostMapping("notify")
	public void acceptPaymentResult(@RequestBody Map<String, String> body) {
		//log.info("Notify from payment provider");
		if (body != null && body.get("payload") != null) {
			paymentService.handlePaymentResult(body.get("payload"));
		}
	}  
	
	@GetMapping("{invoiceNo:\\d+}")
	public ShopSubscriptionDTO getSubscription(@PathVariable long invoiceNo) {
		return shopSubscriptionService.getSubscription(invoiceNo);
	}
	
}
