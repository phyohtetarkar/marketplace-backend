package com.marketplace.api.vendor.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.consumer.order.OrderDTO;
import com.marketplace.domain.order.OrderQuery;
import com.marketplace.domain.order.usecase.CancelOrderBySellerUseCase;
import com.marketplace.domain.order.usecase.CancelOrderItemUseCase;
import com.marketplace.domain.order.usecase.CompleteOrderUseCase;
import com.marketplace.domain.order.usecase.ConfirmOrderUseCase;
import com.marketplace.domain.order.usecase.GetAllOrderByQueryUseCase;
import com.marketplace.domain.order.usecase.GetOrderByIdUseCase;

@Component
public class OrderControllerFacade {

	@Autowired
	private CancelOrderBySellerUseCase cancelOrderBySellerUseCase;
	
	@Autowired
	private ConfirmOrderUseCase confirmOrderUseCase;
	
	@Autowired
	private CompleteOrderUseCase completeOrderUseCase;
	
	@Autowired
	private GetOrderByIdUseCase getOrderByIdUseCase;
	
	@Autowired
	private GetAllOrderByQueryUseCase getAllOrderByQueryUseCase;
	
	@Autowired
	private CancelOrderItemUseCase cancelOrderItemUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;
	
	public void cancelOrder(long userId, long orderId) {
		cancelOrderBySellerUseCase.apply(userId, orderId);
	}
	
	public void confirmOrder(long userId, long orderId) {
		confirmOrderUseCase.apply(userId, orderId);
	}
	
	public void completeOrder(long userId, long orderId) {
		completeOrderUseCase.apply(userId, orderId);
	}
	
	public void cancelOrderItem(long userId, long orderItemId) {
		cancelOrderItemUseCase.apply(userId, orderItemId);
	}
	
	public OrderDTO getOrderById(long id) {
		var source = getOrderByIdUseCase.apply(id);
		return mapper.map(source);
	}
	
	public PageDataDTO<OrderDTO> getOrders(OrderQuery query) {
		return mapper.mapOrderPage(getAllOrderByQueryUseCase.apply(query));
	}
}
