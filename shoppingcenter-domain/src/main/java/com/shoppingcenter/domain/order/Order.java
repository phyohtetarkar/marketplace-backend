package com.shoppingcenter.domain.order;

import java.math.BigDecimal;
import java.util.List;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

	public enum Status {
		PENDING, CONFIRMED, COMPLETED, CANCELLED
	}

	public enum PaymentMethod {
		COD, BANK_TRANSFER
	}

	private long id;

	private String orderCode;

	private BigDecimal subTotalPrice;

	private BigDecimal totalPrice;

	private BigDecimal discount;

	private int quantity;

	private String note;

	private Order.Status status;

	private Order.PaymentMethod paymentMethod;

	private DeliveryDetail delivery;

	private PaymentDetail payment;

	private List<OrderItem> items;

	private User user;

	private Shop shop;
	
	private long userId;
	
	private long shopId;

	private long createdAt;

	public Order() {
		this.subTotalPrice = BigDecimal.valueOf(0);
		this.totalPrice = BigDecimal.valueOf(0);
		this.discount = BigDecimal.valueOf(0);
	}

}
