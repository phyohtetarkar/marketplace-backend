package com.shoppingcenter.domain.order;

import java.math.BigDecimal;
import java.util.Set;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductVariantAttribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {

	private long id;

	private long orderId;

	private long productId;
	
	private Long productVariantId;

	private String productName;

	private String productSlug;
	
	private String productThumbnail;

	private BigDecimal unitPrice;

	private BigDecimal discount;

	private int quantity;
	
	private boolean cancelled;

	private Set<ProductVariantAttribute> attributes;

	private Product product;
	
	private Order order;

	public OrderItem() {
		this.unitPrice = BigDecimal.valueOf(0);
		this.discount = BigDecimal.valueOf(0);
	}

	public BigDecimal getSubTotalPrice() {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}

	public BigDecimal getTotalPrice() {
		return getSubTotalPrice().subtract(discount.multiply(BigDecimal.valueOf(quantity)));
	}

}
