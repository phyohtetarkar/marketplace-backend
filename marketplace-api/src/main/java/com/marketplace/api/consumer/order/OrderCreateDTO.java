package com.marketplace.api.consumer.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marketplace.api.consumer.shoppingcart.CartItemEditDTO;
import com.marketplace.domain.order.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateDTO {

	private long shopId;

	@JsonIgnore
	private long userId;

	private String note;

	private Order.PaymentMethod paymentMethod;

	private List<CartItemEditDTO> cartItems;

	private DeliveryDetailDTO delivery;

	private PaymentDetailDTO payment;

}
