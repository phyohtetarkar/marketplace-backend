package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopSetting")
@Table(name = Constants.TABLE_PREFIX + "shop_setting")
public class ShopSettingEntity extends AuditingEntity {

	@Id
	private long id;
	
	private boolean cashOnDelivery;
	
	private boolean bankTransfer;
	
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private ShopEntity shop;
}
