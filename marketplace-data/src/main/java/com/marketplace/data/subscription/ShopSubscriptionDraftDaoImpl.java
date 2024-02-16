package com.marketplace.data.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.shop.ShopRepo;
import com.marketplace.domain.subscription.ShopSubscriptionDraft;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDraftDao;

@Repository
public class ShopSubscriptionDraftDaoImpl implements ShopSubscriptionDraftDao {

	@Autowired
	private ShopSubscriptionDraftRepo repo;
	
	@Autowired
	private ShopRepo shopRepo;
	
	@Override
	public ShopSubscriptionDraft create(ShopSubscriptionDraft values) {
		var entity = new ShopSubscriptionDraftEntity();
		entity.setTitle(values.getTitle());
		entity.setSubTotalPrice(values.getSubTotalPrice());
		entity.setTotalPrice(values.getTotalPrice());
		entity.setDiscount(values.getDiscount());
		entity.setDuration(values.getDuration());
		entity.setPromoCode(values.getPromoCode());
		entity.setShop(shopRepo.getReferenceById(values.getShop().getId()));
		
		var result = repo.save(entity);
		return ShopSubscriptionDraftMapper.toDomain(result);
	}

	@Override
	public void deleteByCreatedAtLessThan(long createdAt) {
		repo.deleteByCreatedAtLessThan(createdAt);
	}

	@Override
	public boolean existsById(long id) {
		return repo.existsById(id);
	}

	@Override
	public ShopSubscriptionDraft findById(long id) {
		return repo.findById(id).map(ShopSubscriptionDraftMapper::toDomain).orElse(null);
	}

}
