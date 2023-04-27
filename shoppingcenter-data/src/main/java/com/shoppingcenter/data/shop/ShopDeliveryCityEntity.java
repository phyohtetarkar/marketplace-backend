package com.shoppingcenter.data.shop;

import java.io.Serializable;
import java.util.Objects;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.misc.CityEntity;
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
@Entity(name = "ShopDeliveryCity")
@Table(name = Constants.TABLE_PREFIX + "shop_delivery_city")
public class ShopDeliveryCityEntity extends AuditingEntity {
	
	@EmbeddedId
	private ShopDeliveryCityEntity.ID id;
	
	@MapsId("city_id")
	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityEntity city;
	
	@MapsId("shop_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private ShopEntity shop;
	
	public ShopDeliveryCityEntity() {
		this.id = new ID();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "shop_id")
		private long shopId;

		@Column(name = "city_id")
		private long cityId;
		
		public ID() {
		}

		public ID(long shopId, long cityId) {
			super();
			this.shopId = shopId;
			this.cityId = cityId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cityId, shopId);
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
			return cityId == other.cityId && shopId == other.shopId;
		}

	} 
	
}
