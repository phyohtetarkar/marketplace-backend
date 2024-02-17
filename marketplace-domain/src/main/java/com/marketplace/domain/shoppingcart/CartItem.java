package com.marketplace.domain.shoppingcart;

import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductVariant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

	private long userId;

	private int quantity;

	private Product product;

	private ProductVariant variant;

	public CartItem() {
	}

	@Getter
	@Setter
	public static class ID {

		private long userId;

		private long productId;

		private long variantId;
		
		public ID() {
		}

		public ID(long userId, long productId, long variantId) {
			this.userId = userId;
			this.productId = productId;
			this.variantId = variantId;
		}

	}
}
