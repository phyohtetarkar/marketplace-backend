package com.marketplace.data.subscription;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.shop.ShopEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.subscription.ShopSubscription;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSubscription")
@Table(name = Constants.TABLE_PREFIX + "shop_subscription")
public class ShopSubscriptionEntity extends AuditingEntity {

	@Id
	private long invoiceNo;

	@Column(columnDefinition = "TEXT")
	private String title;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal subTotalPrice;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal discount;

	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	private ShopSubscription.Status status;

	private int duration;

	private String promoCode;

	@OneToOne(mappedBy = "shopSubscription", cascade = CascadeType.REMOVE)
	private ShopSubscriptionTimeEntity time;

	@ManyToOne
	private ShopEntity shop;

	public ShopSubscriptionEntity() {
		this.subTotalPrice = BigDecimal.ZERO;
		this.totalPrice = BigDecimal.ZERO;
		this.discount = BigDecimal.ZERO;
	}

	public long getStartAt() {
		if (time == null) {
			return 0;
		}
		return time.getStartAt();
	}

	public long getEndAt() {
		if (time == null) {
			return 0;
		}
		return time.getEndAt();
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
