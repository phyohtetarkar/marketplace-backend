package com.marketplace.data.shoppingcart;

import java.io.Serializable;
import java.util.Objects;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.product.ProductEntity;
import com.marketplace.data.product.ProductVariantEntity;
import com.marketplace.data.user.UserEntity;
import com.marketplace.domain.Constants;

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

	@EmbeddedId
	private CartItemEntity.ID id;

	private int quantity;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@MapsId("productId")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	@ManyToOne
	@JoinColumn(name = "product_variant_id")
	private ProductVariantEntity variant;

	public CartItemEntity() {
		this.id = new ID();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "user_id")
		private long userId;

		@Column(name = "product_id")
		private long productId;

		@Column(name = "variant_id")
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
			return Objects.hash(productId, userId, variantId);
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
			return productId == other.productId && userId == other.userId && variantId == other.variantId;
		}

	}
}
