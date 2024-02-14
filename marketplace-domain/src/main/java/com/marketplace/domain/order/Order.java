package com.marketplace.domain.order;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.domain.Audit;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.user.User;

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

	private BigDecimal discountPrice;

	private int quantity;

	private String note;

	private Order.Status status;

	private Order.PaymentMethod paymentMethod;

	private DeliveryDetail delivery;

	private PaymentDetail payment;

	private List<OrderItem> items;

	private User customer;

	private Shop shop;

	private Audit audit = new Audit();

	public Order() {
		this.subTotalPrice = BigDecimal.valueOf(0);
		this.totalPrice = BigDecimal.valueOf(0);
		this.discountPrice = BigDecimal.valueOf(0);
	}

	public long getCreatedAt() {
		return audit.getCreatedAt();
	}
}
