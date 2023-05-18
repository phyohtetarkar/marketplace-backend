package com.shoppingcenter.data.shop;

import com.shoppingcenter.domain.shop.ShopSubscription;

public class ShopSubscriptionMapper {
	
	public static ShopSubscription toDomain(ShopSubscriptionEntity entity) {
		var subscription = toDomainCompat(entity);
		if (entity.getShop() != null) {
			subscription.setShop(ShopMapper.toDomainCompat(entity.getShop()));
		}
		return subscription;
	}

	public static ShopSubscription toDomainCompat(ShopSubscriptionEntity entity) {
		var subscription = new ShopSubscription();
		subscription.setId(entity.getId());
		subscription.setTitle(entity.getTitle());
		subscription.setTotalPrice(entity.getTotalPrice());
		subscription.setSubTotalPrice(entity.getSubTotalPrice());
		subscription.setDiscount(entity.getDiscount());
		subscription.setDuration(entity.getDuration());
		subscription.setStartAt(entity.getStartAt());
		subscription.setEndAt(entity.getEndAt());
		subscription.setActive(entity.isActive());
		subscription.setCreatedAt(entity.getCreatedAt());
		return subscription;
	}
	
}
