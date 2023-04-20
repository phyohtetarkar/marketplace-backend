package com.shoppingcenter.data.shop;

import java.io.Serializable;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopMember")
@Table(name = Constants.TABLE_PREFIX + "shop_member")
public class ShopMemberEntity extends AuditingEntity {

	@EmbeddedId
	private ShopMemberEntity.ID id;

	@MapsId("shop_id")
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private ShopEntity shop;

	@MapsId("user_id")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	private String role;

	public ShopMemberEntity() {
		this.id = new ID();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "shop_id")
		private long shopId;

		@Column(name = "user_id")
		private long userId;

		public ID() {
		}

		public ID(long shopId, long userId) {
			this.shopId = shopId;
			this.userId = userId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (shopId ^ (shopId >>> 32));
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
			if (shopId != other.shopId)
				return false;
			if (userId != other.userId)
				return false;
			return true;
		}

	}
}
