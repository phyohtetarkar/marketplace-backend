package com.marketplace.data.subscription;

import com.marketplace.data.AuditMapper;
import com.marketplace.data.shop.ShopMapper;
import com.marketplace.domain.subscription.ShopSubscription;

public interface ShopSubscriptionMapper {

	public static ShopSubscription toDomain(ShopSubscriptionEntity entity) {
		var subscription = new ShopSubscription();
		subscription.setInvoiceNo(entity.getInvoiceNo());
		subscription.setTitle(entity.getTitle());
		subscription.setTotalPrice(entity.getTotalPrice());
		subscription.setSubTotalPrice(entity.getSubTotalPrice());
		subscription.setDiscount(entity.getDiscount());
		subscription.setDuration(entity.getDuration());
		subscription.setStartAt(entity.getStartAt());
		subscription.setEndAt(entity.getEndAt());
		subscription.setStatus(entity.getStatus());
		subscription.setPromoCode(entity.getPromoCode());
		subscription.setAudit(AuditMapper.from(entity));
		
		if (entity.getShop() != null) {
			subscription.setShop(ShopMapper.toDomainCompat(entity.getShop()));
		}
		return subscription;
	}
	
}
