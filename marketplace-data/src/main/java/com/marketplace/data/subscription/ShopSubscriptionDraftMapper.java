package com.marketplace.data.subscription;

import com.marketplace.data.AuditMapper;
import com.marketplace.data.shop.ShopMapper;
import com.marketplace.domain.subscription.ShopSubscriptionDraft;

public interface ShopSubscriptionDraftMapper {

	static ShopSubscriptionDraft toDomain(ShopSubscriptionDraftEntity entity) {
		var draft = new ShopSubscriptionDraft();
		draft.setId(entity.getId());
		draft.setTitle(entity.getTitle());
		draft.setSubTotalPrice(entity.getSubTotalPrice());
		draft.setTotalPrice(entity.getTotalPrice());
		draft.setDiscount(entity.getDiscount());
		draft.setDuration(entity.getDuration());
		draft.setPromoCode(entity.getPromoCode());
		draft.setShop(ShopMapper.toDomainCompat(entity.getShop()));
		draft.setAudit(AuditMapper.from(entity));
		return draft;
	}
	
}
