package com.marketplace.data.shop;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
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
	
	@Column(columnDefinition = "TEXT")
	private String orderNote;
	
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private ShopEntity shop;
}
