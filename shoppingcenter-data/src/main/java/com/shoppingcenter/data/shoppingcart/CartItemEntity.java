package com.shoppingcenter.data.shoppingcart;

import java.io.Serializable;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.variant.ProductVariantEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CartItem")
@Table(name = Constants.TABLE_PREFIX + "cart_item")
public class CartItemEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CartItemEntity.ID id;

	private int quantity;

	@MapsId("user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@MapsId("product_id")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	@MapsId
	@ManyToOne
	@JoinColumn(name = "variant_id")
	private ProductVariantEntity variant;

	public CartItemEntity() {
		this.id = new ID();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		@Column(name = "user_id")
		private long userId;

		@Column(name = "product_id")
		private long productId;

		private long variantId;

		public ID() {
		}

		public ID(long userId, long productId) {
			this(userId, productId, 0);
		}

		public ID(long userId, long productId, long variantId) {
			this.userId = userId;
			this.productId = productId;
			this.variantId = variantId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (userId ^ (userId >>> 32));
			result = prime * result + (int) (productId ^ (productId >>> 32));
			result = prime * result + (int) (variantId ^ (variantId >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ID other = (ID) obj;
			if (userId != other.userId)
				return false;
			if (productId != other.productId)
				return false;
			if (variantId != other.variantId)
				return false;
			return true;
		}

	}
}
