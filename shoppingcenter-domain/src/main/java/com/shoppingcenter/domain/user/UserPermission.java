package com.shoppingcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPermission {

	public enum Permission {
		DASHBOARD_READ,
		BANNER_READ, 
		BANNER_WRITE,
		BANNER_DELETE,
		CATEGORY_READ, 
		CATEGORY_WRITE, 
		CATEGORY_DELETE, 
		CITY_READ, 
		CITY_WRITE, 
		CITY_DELETE, 
		SHOP_READ, 
		SHOP_WRITE, 
		PRODUCT_READ, 
		PRODUCT_WRITE, 
		USER_READ, 
		USER_WRITE, 
		STAFF_READ, 
		STAFF_WRITE, 
		SUBSCRIPTION_PLAN_READ, 
		SUBSCRIPTION_PLAN_WRITE, 
		SUBSCRIPTION_PLAN_DELETE, 
		PROMO_CODE_READ,
		PROMO_CODE_WRITE,
		PROMO_CODE_DELETE,
		SUBSCRIPTION_HISTORY_READ
	}

	private long id;
	
	private Permission permission;
	
	
}
