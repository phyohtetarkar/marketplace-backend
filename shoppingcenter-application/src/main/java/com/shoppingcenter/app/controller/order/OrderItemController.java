package com.shoppingcenter.app.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.domain.common.AuthenticationContext;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/order-items")
@Tag(name = "Order")
public class OrderItemController {

	@Autowired
	private OrderFacade orderFacade;
	
	@Autowired
    private AuthenticationContext authentication; 
	
	@PutMapping("{id:\\d+}/cancel")
	public void cancelOrderItem(@PathVariable long id) {
		orderFacade.cancelOrderItem(authentication.getUserId(), id);
	}
	
}
