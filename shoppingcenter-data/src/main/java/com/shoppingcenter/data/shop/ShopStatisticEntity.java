package com.shoppingcenter.data.shop;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopStatisticEntity {

	@Id
	private long id;
	
	private int pendingOrder;

	private long totalOrder;

	private int totalProduct;

	private long totalSale;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private ShopEntity shop;

}
