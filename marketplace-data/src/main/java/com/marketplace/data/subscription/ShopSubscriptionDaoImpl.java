package com.marketplace.data.subscription;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.JpaSpecificationBuilder;
import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.shop.ShopRepo;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.ShopSubscriptionTime;
import com.marketplace.domain.subscription.ShopSubscription.Status;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDao;

@Repository
public class ShopSubscriptionDaoImpl implements ShopSubscriptionDao {

	@Autowired
	private ShopSubscriptionRepo shopSubscriptionRepo;

	@Autowired
	private ShopRepo shopRepo;
	
	@Autowired
	private ShopSubscriptionTimeRepo shopSubscriptionTimeRepo;

	@Override
	public ShopSubscription save(ShopSubscription subscription) {
		var entity = shopSubscriptionRepo.findById(subscription.getInvoiceNo()).orElseGet(ShopSubscriptionEntity::new);
		entity.setInvoiceNo(subscription.getInvoiceNo());
		entity.setTitle(subscription.getTitle());
		entity.setSubTotalPrice(subscription.getSubTotalPrice());
		entity.setTotalPrice(subscription.getTotalPrice());
		entity.setDiscount(subscription.getDiscount());
		entity.setDuration(subscription.getDuration());
		entity.setStatus(subscription.getStatus());
		entity.setPromoCode(subscription.getPromoCode());
		entity.setShop(shopRepo.getReferenceById(subscription.getShop().getId()));

		var result = shopSubscriptionRepo.save(entity);
		
		return ShopSubscriptionMapper.toDomain(result);
	}
	
	@Override
	public void saveTime(ShopSubscriptionTime time) {
		var entity = new ShopSubscriptionTimeEntity();
		entity.getId().setShopId(time.getShopId());
		entity.getId().setStartAt(time.getStartAt());
		entity.getId().setEndAt(time.getEndAt());
		
		var ss = shopSubscriptionRepo.getReferenceById(time.getInvoiceNo());
		
		var result = shopSubscriptionTimeRepo.save(entity);
		
		result.setShopSubscription(ss);
	}
	
	@Override
	public boolean existsByShopIdAndStatusAndStartAt(long shopId, Status status, long startAt) {
		return shopSubscriptionRepo.existsByShopIdAndStatusAndTime_Id_StartAt(shopId, status, startAt);
	}
	
	@Override
	public BigDecimal getTotalSubscriptionPrice() {
		return shopSubscriptionRepo.getTotalSubscriptionPrice();
	}

	@Override
	public List<ShopSubscription> findLatest10SuccessfulSubscription() {
		return shopSubscriptionRepo.findTop10ByStatusOrderByModifiedAtDesc(ShopSubscription.Status.SUCCESS).stream()
				.map(ShopSubscriptionMapper::toDomain)
				.toList();
	}

	@Override
	public ShopSubscription findByInvoiceNo(long invoiceNo) {
		return shopSubscriptionRepo.findByInvoiceNo(invoiceNo)
				.map(ShopSubscriptionMapper::toDomain)
				.orElse(null);
	}

	@Override
	public ShopSubscription findShopSubscriptionByShopAndTime(long shopId, long time) {
		var status = ShopSubscription.Status.SUCCESS;
		return shopSubscriptionRepo.findByShopIdAndStatusAndTime_Id_StartAtLessThanEqualAndTime_Id_EndAtGreaterThanEqual(shopId, status, time, time)
				.map(ShopSubscriptionMapper::toDomain)
				.orElse(null);
	}
	
	@Override
	public ShopSubscription findLatestSubscriptionByShop(long shopId) {
		var status = ShopSubscription.Status.SUCCESS;
		return shopSubscriptionRepo.findTopByShopIdAndStatusOrderByTime_Id_StartAtDesc(shopId, status)
				.map(ShopSubscriptionMapper::toDomain)
				.orElse(null);
	}

	@Override
	public List<ShopSubscription> findShopPreSubscriptions(long shopId, long startAt) {
		var status = ShopSubscription.Status.SUCCESS;
		return shopSubscriptionRepo
				.findByShopIdAndStatusAndTime_Id_StartAtGreaterThanOrderByTime_Id_StartAtDesc(shopId, status, startAt).stream()
				.map(ShopSubscriptionMapper::toDomain)
				.toList();
	}

	@Override
	public PageData<ShopSubscription> findAll(SearchQuery searchQuery) {
		var spec = JpaSpecificationBuilder.build(searchQuery.getCriterias(), ShopSubscriptionEntity.class);

        var pageable = PageQueryMapper.fromPageQuery(searchQuery.getPageQuery());

        var pageResult = spec != null ? shopSubscriptionRepo.findAll(spec, pageable) : shopSubscriptionRepo.findAll(pageable);
        
		return PageDataMapper.map(pageResult, ShopSubscriptionMapper::toDomain);
	}

}
