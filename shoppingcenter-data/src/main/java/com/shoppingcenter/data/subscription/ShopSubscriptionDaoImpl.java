package com.shoppingcenter.data.subscription;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.ShopSubscription;
import com.shoppingcenter.domain.subscription.ShopSubscription.Status;
import com.shoppingcenter.domain.subscription.ShopSubscriptionQuery;

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
		entity.setPromoCode(subscription.getPromoCode());
		entity.setShop(shopRepo.getReferenceById(subscription.getShopId()));

		var result = shopSubscriptionRepo.save(entity);
		
		return result.getId();
	}

	@Override
	public void deleteById(long id) {
		shopSubscriptionRepo.deleteById(id);
	}
	
	@Override
	public void deleteByStatusNullCreatedAtLessThan(long createdAt) {
		shopSubscriptionRepo.deleteByStatusIsNullAndCreatedAtLessThan(createdAt);
	}
	
	@Override
	public boolean existsByShopIdAndStatusAndStartAt(long shopId, Status status, long startAt) {
		return shopSubscriptionRepo.existsByShopIdAndStatusAndStartAt(shopId, status, startAt);
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
		Specification<ShopSubscriptionEntity> spec = null;
		
		ZoneId zoneId = ZoneOffset.UTC;
		
		try {
			zoneId = StringUtils.hasText(query.getTimeZone()) ? ZoneId.of(query.getTimeZone()): ZoneOffset.UTC;
		} catch (Exception e) {
		}
		
		//System.out.println(zoneId);
		
		if (query.getShopId() != null && query.getShopId() > 0) {
			Specification<ShopSubscriptionEntity> shopSpec = new BasicSpecification<>(
                    new SearchCriteria("id", Operator.EQUAL, query.getShopId(), "shop"));
            spec = Specification.where(shopSpec);
		}
		
		if (query.getStatus() != null) {
			Specification<ShopSubscriptionEntity> statusSpec = new BasicSpecification<>(
					new SearchCriteria("status", Operator.EQUAL, query.getStatus()));
			spec = spec != null ? spec.and(statusSpec) : Specification.where(statusSpec);
		} else {
			Specification<ShopSubscriptionEntity> statusSpec = new BasicSpecification<>(
					new SearchCriteria("status", Operator.NOT_NULL, null));
			spec = spec != null ? spec.and(statusSpec) : Specification.where(statusSpec);
		}
		
		if (StringUtils.hasText(query.getFromDate())) {
			var date = LocalDate.parse(query.getFromDate());
			
			var from = date.atStartOfDay(zoneId).toInstant().toEpochMilli();
			
			Specification<ShopSubscriptionEntity> dateFromSpec = new BasicSpecification<>(
                    new SearchCriteria("createdAt", Operator.GREATER_THAN_EQ, from));
			
 			
			spec = spec != null ? spec.and(dateFromSpec) : Specification.where(dateFromSpec);
		}
		
		if (StringUtils.hasText(query.getToDate())) {
			var date = LocalDate.parse(query.getToDate());
			
			var to = date.atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli();
			
			Specification<ShopSubscriptionEntity> dateToSpec = new BasicSpecification<>(
                    new SearchCriteria("createdAt", Operator.LESS_THAN_EQ, to));
			
 			
			spec = spec != null ? spec.and(dateToSpec) : Specification.where(dateToSpec);
		}

		var sort = Sort.by(Order.desc("createdAt"));

		var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

		var pageResult = shopSubscriptionRepo.findAll(spec, pageable);
		return PageDataMapper.map(pageResult, ShopSubscriptionMapper::toDomain);
	}

}
