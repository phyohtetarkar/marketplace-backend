package com.marketplace.domain.order;

import java.math.BigDecimal;

import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {

	private long id;

	private BigDecimal unitPrice;

	private BigDecimal discountPrice;

	private int quantity;
	
	private boolean cancelled;

	private Product product;
	
	private ProductVariant productVariant;
	
	private long orderId;
	
	public OrderItem() {
		this.unitPrice = BigDecimal.valueOf(0);
		this.discountPrice = BigDecimal.valueOf(0);
	}
	
	public BigDecimal getDiscountPrice() {
		if (discountPrice == null) {
			return BigDecimal.valueOf(0);
		}
		return discountPrice.multiply(BigDecimal.valueOf(quantity));
	}

	public BigDecimal getSubTotalPrice() {
		if (unitPrice == null) {
			return BigDecimal.valueOf(0);
		}
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}

	public BigDecimal getTotalPrice() {
		return getSubTotalPrice().subtract(getDiscountPrice());
	}

}
