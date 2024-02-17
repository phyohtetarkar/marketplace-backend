package com.marketplace.data.product;

import java.io.Serializable;

import com.marketplace.data.AuditingEntity;
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
@Entity(name = "FavoriteProduct")
@Table(name = Constants.TABLE_PREFIX + "favorite_product")
public class FavoriteProductEntity extends AuditingEntity {

	@EmbeddedId
	private FavoriteProductEntity.ID id;

	@MapsId("productId")
	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public FavoriteProductEntity() {
		this.id = new ID();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "product_id")
		private long productId;

		@Column(name = "user_id")
		private long userId;

		public ID() {
		}

		public ID(long productId, long userId) {
			this.productId = productId;
			this.userId = userId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (productId ^ (productId >>> 32));
			result = prime * result + (int) (userId ^ (userId >>> 32));
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
			if (productId != other.productId)
				return false;
			if (userId != other.userId)
				return false;
			return true;
		}

	}

}
