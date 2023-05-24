package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.ShopSubscriptionQuery;
import com.shoppingcenter.domain.shop.ShopSubscription.Status;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;

@Repository
public class ShopSubscriptionDaoImpl implements ShopSubscriptionDao {

	@Autowired
	private ShopSubscriptionRepo shopSubscriptionRepo;

	@Autowired
	private ShopRepo shopRepo;

	@Override
	public long save(ShopSubscription subscription) {
		var entity = shopSubscriptionRepo.findById(subscription.getId()).orElseGet(ShopSubscriptionEntity::new);
		entity.setTitle(subscription.getTitle());
		entity.setSubTotalPrice(subscription.getSubTotalPrice());
		entity.setTotalPrice(subscription.getTotalPrice());
		entity.setDiscount(subscription.getDiscount());
		entity.setDuration(subscription.getDuration());
		entity.setStartAt(subscription.getStartAt());
		entity.setEndAt(subscription.getEndAt());
		entity.setStatus(subscription.getStatus());
		entity.setPreSubscription(subscription.isPreSubscription());
		entity.setShop(shopRepo.getReferenceById(subscription.getShopId()));

		var result = shopSubscriptionRepo.save(entity);
		
		return result.getId();
	}

	@Override
	public void deleteById(long id) {
		shopSubscriptionRepo.deleteById(id);
	}
	
	@Override
	public void deleteByStatusCreatedAtLessThan(Status status, long createdAt) {
		shopSubscriptionRepo.deleteByStatusAndCreatedAtLessThan(status, createdAt);
	}

	@Override
	public ShopSubscription findById(long id) {
		return shopSubscriptionRepo.findById(id).map(ShopSubscriptionMapper::toDomain).orElse(null);
	}

	@Override
	public ShopSubscription findCurrentSubscriptionByShop(long shopId) {
		var status = ShopSubscription.Status.SUCCESS;
		var currentTime = System.currentTimeMillis();
		return shopSubscriptionRepo.findByShopIdAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(shopId, status, currentTime, currentTime)
				.map(ShopSubscriptionMapper::toDomainCompat)
				.orElse(null);
	}
	
	@Override
	public ShopSubscription findLatestSubscriptionByShop(long shopId) {
		var status = ShopSubscription.Status.SUCCESS;
		return shopSubscriptionRepo.findTopByShopIdAndStatusOrderByStartAtDesc(shopId, status)
				.map(ShopSubscriptionMapper::toDomainCompat)
				.orElse(null);
	}

	@Override
	public List<ShopSubscription> findShopPreSubscriptions(long shopId) {
		var status = ShopSubscription.Status.SUCCESS;
		return shopSubscriptionRepo
				.findByShopIdAndStatusAndPreSubscriptionTrueOrderByStartAtDesc(shopId, status).stream()
				.map(ShopSubscriptionMapper::toDomainCompat).toList();
	}

	@Override
	public PageData<ShopSubscription> findAll(ShopSubscriptionQuery query) {
		var statusSpec = new BasicSpecification<ShopSubscriptionEntity>(
				new SearchCriteria("status", Operator.EQUAL, ShopSubscription.Status.SUCCESS));

		Specification<ShopSubscriptionEntity> spec = Specification.where(statusSpec);

		var sort = Sort.by(Order.desc("createdAt"));

		var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

		var pageResult = shopSubscriptionRepo.findAll(spec, pageable);
		return PageDataMapper.map(pageResult, ShopSubscriptionMapper::toDomain);
	}

}
