package com.shoppingcenter.domain.shop;

import java.util.List;

import com.shoppingcenter.domain.misc.City;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDeliveryCityInput {

	private long shopId;
	
	private List<City> cities;
	
}
