package com.marketplace.data.shop;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.shop.Shop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopStatusHistory")
@Table(name = Constants.TABLE_PREFIX + "shop_status_history")
public class ShopStatusHistoryEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Shop.Status status;
	
	@Column(columnDefinition = "TEXT")
	private String remark;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ShopEntity shop;
	
}
