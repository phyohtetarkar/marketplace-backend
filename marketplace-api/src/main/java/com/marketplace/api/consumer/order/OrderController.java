package com.marketplace.api.consumer.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.AuthenticationUtil;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name = "Consumer")
public class OrderController {

	@Autowired
	private OrderControllerFacade orderFacade;
	
	@PostMapping
	public String create(@ModelAttribute OrderCreateDTO body) {
		body.setUserId(AuthenticationUtil.getAuthenticatedUserId());
		
		return orderFacade.createOrder(body);
	}
	
	@PutMapping("{orderId:\\d+}/cancel")
	public void cancelOrder(@PathVariable long orderId) {
		orderFacade.cancelOrder(AuthenticationUtil.getAuthenticatedUserId(), orderId);
	}
	
	@PutMapping("{orderId:\\d+}/upload-receipt")
	public void uploadReceiptImage(@PathVariable long orderId, @RequestPart MultipartFile file) {
		orderFacade.uploadReceiptImage(AuthenticationUtil.getAuthenticatedUserId(), orderId, file);
	}
	
	@GetMapping("{code}")
	public OrderDTO getOrder(@PathVariable String code) {
		return orderFacade.getOrderByCode(code);
	}
}
