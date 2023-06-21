package com.shoppingcenter.data.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.subscription.SubscriptionPromo;
import com.shoppingcenter.domain.subscription.SubscriptionPromoDao;
import com.shoppingcenter.domain.subscription.SubscriptionPromoQuery;

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
		if (entity.isUsed()) {
			throw new ApplicationException("Cannot update used promo code");
		}
		
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
	public PageData<SubscriptionPromo> findAll(SubscriptionPromoQuery query) {
		var sort = Sort.by(Order.desc("createdAt"));
		var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

		if (query.getAvailable() == Boolean.TRUE) {
			return PageDataMapper.map(subscriptionPromoRepo.findByUsedFalseAndExpiredAtGreaterThanEqual(
					System.currentTimeMillis(), pageable), SubscriptionPromoMapper::toDomain);
		}

		if (query.getUsed() == Boolean.TRUE) {
			return PageDataMapper.map(subscriptionPromoRepo.findByUsedTrue(pageable),
					SubscriptionPromoMapper::toDomain);
		}

		if (query.getExpired() == Boolean.TRUE) {
			return PageDataMapper.map(
					subscriptionPromoRepo.findByExpiredAtLessThan(System.currentTimeMillis(), pageable),
					SubscriptionPromoMapper::toDomain);
		}

		return PageDataMapper.map(subscriptionPromoRepo.findAll(pageable), SubscriptionPromoMapper::toDomain);
	}

}
