package com.marketplace.api.vendor.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.order.OrderDTO;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.OrderQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/vendor/shops/{shopId:\\d+}/orders")
@PreAuthorize("@authz.isShopMember(#shopId)")
@Tag(name = "Vendor")
public class OrderController {

	@Autowired
	private OrderControllerFacade orderFacade;

	@PutMapping("{orderId:\\d+}/confirm")
	public void confirmOrder(@PathVariable long shopId, @PathVariable long orderId) {
		orderFacade.confirmOrder(AuthenticationUtil.getAuthenticatedUserId(), orderId);
	}

	@PutMapping("{orderId:\\d+}/complete")
	public void completeOrder(@PathVariable long shopId, @PathVariable long orderId) {
		orderFacade.completeOrder(AuthenticationUtil.getAuthenticatedUserId(), orderId);
	}

	@PutMapping("{orderId:\\d+}/cancel")
	public void cancelOrder(@PathVariable long shopId, @PathVariable long orderId) {
		orderFacade.cancelOrder(AuthenticationUtil.getAuthenticatedUserId(), orderId);
	}

	@PutMapping("{orderId:\\d+}/items/{itemId:\\d+}/cancel")
	public void cancelOrderItem(@PathVariable long shopId, @PathVariable long orderId, @PathVariable long itemId) {
		orderFacade.cancelOrderItem(AuthenticationUtil.getAuthenticatedUserId(), itemId);
	}

	@GetMapping("{orderId:\\d+}")
	public OrderDTO getOrder(@PathVariable long shopId, @PathVariable long orderId) {
		return orderFacade.getOrderById(orderId);
	}

	@GetMapping
	public PageDataDTO<OrderDTO> getOrders(
			@PathVariable long shopId, 
			@RequestParam(required = false) String date,
			@RequestParam(required = false) String code, 
			@RequestParam(required = false) Order.Status status,
			@RequestParam(required = false, name = "time-zone") String timeZone,
			@RequestParam(required = false) Integer page) {

		var query = OrderQuery.builder()
				.shopId(shopId)
				.date(date)
				.code(code)
				.status(status)
				.timeZone(timeZone)
				.page(page).build();
		return orderFacade.getOrders(query);
	}
}
