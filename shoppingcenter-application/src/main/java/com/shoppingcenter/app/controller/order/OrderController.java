package com.shoppingcenter.app.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.order.dto.OrderCreateDTO;
import com.shoppingcenter.app.controller.order.dto.OrderDTO;
import com.shoppingcenter.app.controller.shop.ShopMemberFacade;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.OrderQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name = "Order")
public class OrderController {
	
	@Autowired
	private OrderFacade orderFacade;
	
	@Autowired
	private ShopMemberFacade shopMemberFacade;

	@Autowired
    private AuthenticationContext authentication; 
	
	@PostMapping
	public String create(@ModelAttribute OrderCreateDTO data) {
		data.setUserId(authentication.getUserId());
		
		return orderFacade.createOrder(data);
	}
	
	@PutMapping("{orderId:\\d+}/confirm")
	public void confirmOrder(@PathVariable long orderId) {
		orderFacade.confirmOrder(authentication.getUserId(), orderId);
	}
	
	@PutMapping("{orderId:\\d+}/complete")
	public void completeOrder(@PathVariable long orderId) {
		orderFacade.completeOrder(authentication.getUserId(), orderId);
	}
	
	@PutMapping("{orderId:\\d+}/cancel")
	public void cancelOrder(@PathVariable long orderId) {
		orderFacade.cancelOrder(authentication.getUserId(), orderId);
	}
	
	@PutMapping("{orderId:\\d+}/upload-receipt")
	public void uploadReceiptImage(@PathVariable long orderId, @RequestPart MultipartFile file) {
		orderFacade.uploadReceiptImage(authentication.getUserId(), orderId, file);
	}
	
	@GetMapping("{code}")
	public OrderDTO getOrder(@PathVariable String code) {
		return orderFacade.getOrderByCode(code);
	}
	
	@GetMapping
	public PageDataDTO<OrderDTO> getOrders(
			@RequestParam(name = "shop-id") long shopId,
			@RequestParam(required = false) String date,
			@RequestParam(required = false) Order.Status status,
			@RequestParam(name = "time-zone", required = false) String timeZone,
			@RequestParam(required = false) Integer page) {
		
		if (!shopMemberFacade.isMember(shopId, authentication.getUserId())) {
			return null;
		}
		
		var query = OrderQuery.builder()
				.shopId(shopId)
				.date(date)
				.status(status)
				.timeZone(timeZone)
				.page(page)
				.build();
		return orderFacade.getOrders(query);
	}
	
}
