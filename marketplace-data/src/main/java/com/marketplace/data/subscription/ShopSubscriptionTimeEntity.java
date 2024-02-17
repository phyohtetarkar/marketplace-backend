package com.marketplace.data.subscription;

import java.io.Serializable;
import java.util.Objects;

import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSubscriptionTime")
@Table(name = Constants.TABLE_PREFIX + "shop_subscription_time")
public class ShopSubscriptionTimeEntity {
	
	@EmbeddedId
	private ShopSubscriptionTimeEntity.ID id;
	
	@Version
	private long version;
	
	@OneToOne(fetch = FetchType.LAZY)
	private ShopSubscriptionEntity shopSubscription;
	
	public ShopSubscriptionTimeEntity() {
		this.id = new ID();
	}
	
	public long getStartAt() {
		return this.id.getStartAt();
	}
	
	public long getEndAt() {
		return this.id.getEndAt();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "shop_id")
		private long shopId;

		@Column(name = "start_at")
		private long startAt;

		@Column(name = "end_at")
		private long endAt;

		public ID() {
		}

		public ID(long shopId, long startAt, long endAt) {
			super();
			this.shopId = shopId;
			this.startAt = startAt;
			this.endAt = endAt;
		}

		@Override
		public int hashCode() {
			return Objects.hash(endAt, shopId, startAt);
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
			return endAt == other.endAt && shopId == other.shopId && startAt == other.startAt;
		}

	}
}
