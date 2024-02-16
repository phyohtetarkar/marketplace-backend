package com.marketplace.data.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.subscription.SubscriptionPromo;
import com.marketplace.domain.subscription.dao.SubscriptionPromoDao;

@Repository
public class SubscriptionPromoDaoImpl implements SubscriptionPromoDao {

	@Autowired
	private SubscriptionPromoRepo subscriptionPromoRepo;

	@Override
	public SubscriptionPromo create(SubscriptionPromo promo) {
		var entity = new SubscriptionPromoEntity();
		entity.setCode(promo.getCode());
		entity.setValueType(promo.getValueType());
		entity.setValue(promo.getValue());
		entity.setMinConstraint(promo.getMinConstraint());
		entity.setExpiredAt(promo.getExpiredAt());

		var result = subscriptionPromoRepo.save(entity);

		return SubscriptionPromoMapper.toDomain(result);
	}

	@Override
	public SubscriptionPromo update(SubscriptionPromo promo) {
		var entity = subscriptionPromoRepo.findById(promo.getId()).orElseThrow(() -> new ApplicationException("Promo code not found"));
		entity.setValueType(promo.getValueType());
		entity.setValue(promo.getValue());
		entity.setMinConstraint(promo.getMinConstraint());
		entity.setExpiredAt(promo.getExpiredAt());
		
		var result = subscriptionPromoRepo.save(entity);
		
		return SubscriptionPromoMapper.toDomain(result);
	}

	@Override
	public void updateUsed(long id, boolean used) {
		subscriptionPromoRepo.updateUsed(id, used);
	}

	@Override
	public void deleteById(long id) {
		subscriptionPromoRepo.deleteById(id);
	}

	@Override
	public boolean existsByCode(String code) {
		return subscriptionPromoRepo.existsByCode(code);
	}

	@Override
	public boolean existsById(long id) {
		return subscriptionPromoRepo.existsById(id);
	}

	@Override
	public SubscriptionPromo findById(long id) {
		return subscriptionPromoRepo.findById(id).map(SubscriptionPromoMapper::toDomain).orElse(null);
	}

	@Override
	public SubscriptionPromo findByCode(String code) {
		return subscriptionPromoRepo.findByCode(code).map(SubscriptionPromoMapper::toDomain).orElse(null);
	}

	@Override
	public PageData<SubscriptionPromo> findAvailable(PageQuery pageQuery) {
		var pageable = PageQueryMapper.fromPageQuery(pageQuery);
		return PageDataMapper.map(subscriptionPromoRepo.findByUsedFalseAndExpiredAtGreaterThanEqual(
				System.currentTimeMillis(), pageable), SubscriptionPromoMapper::toDomain);
	}

	@Override
	public PageData<SubscriptionPromo> findUsed(PageQuery pageQuery) {
		var pageable = PageQueryMapper.fromPageQuery(pageQuery);
		return PageDataMapper.map(subscriptionPromoRepo.findByUsedTrue(pageable),
				SubscriptionPromoMapper::toDomain);
	}

	@Override
	public PageData<SubscriptionPromo> findExpired(PageQuery pageQuery) {
		var pageable = PageQueryMapper.fromPageQuery(pageQuery);
		return PageDataMapper.map(
				subscriptionPromoRepo.findByExpiredAtLessThan(System.currentTimeMillis(), pageable),
				SubscriptionPromoMapper::toDomain);
	}

	@Override
	public PageData<SubscriptionPromo> findAll(PageQuery pageQuery) {
		var pageable = PageQueryMapper.fromPageQuery(pageQuery);
		return PageDataMapper.map(subscriptionPromoRepo.findAll(pageable), SubscriptionPromoMapper::toDomain);
	}

}
