package com.marketplace.api.consumer.order;

import java.math.BigDecimal;

import com.marketplace.api.consumer.product.ProductDTO;
import com.marketplace.api.consumer.product.ProductVariantDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

	private long id;

	private BigDecimal unitPrice;

	private BigDecimal discountPrice;

	private int quantity;

	private boolean cancelled;

	private ProductDTO product;
	
	private ProductVariantDTO productVariant;
}
