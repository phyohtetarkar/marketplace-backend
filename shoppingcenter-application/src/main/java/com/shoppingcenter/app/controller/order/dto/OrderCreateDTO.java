package com.shoppingcenter.app.controller.order.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingcenter.domain.order.Order;

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

	private List<Long> cartItems;

	private DeliveryDetailDTO delivery;

	private PaymentDetailDTO payment;

}
