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
		entity.setActive(subscription.isActive());
		entity.setDuration(subscription.getDuration());
		entity.setStartAt(subscription.getStartAt());
		entity.setEndAt(subscription.getEndAt());
		entity.setShop(shopRepo.getReferenceById(subscription.getShopId()));

		var result = shopSubscriptionRepo.save(entity);
		
		return result.getId();
	}

	@Override
	public void deleteById(long id) {
		shopSubscriptionRepo.deleteById(id);
	}

//	@Override
//	public void deleteByInvoiceNumber(String invoiceNo) {
//		shopSubscriptionRepo.deleteByInvoiceNumber(invoiceNo);
//	}
//
//	@Override
//	public boolean existsByInvoiceNumber(String invoiceNo) {
//		return shopSubscriptionRepo.existsByInvoiceNumber(invoiceNo);
//	}

	@Override
	public ShopSubscription findById(long id) {
		return shopSubscriptionRepo.findById(id).map(ShopSubscriptionMapper::toDomain).orElse(null);
	}

//	@Override
//	public ShopSubscription findByInvoiceNumber(String invoiceNo) {
//		return shopSubscriptionRepo.findByInvoiceNumber(invoiceNo).map(ShopSubscriptionMapper::toDomain).orElse(null);
//	}

	@Override
	public ShopSubscription findCurrentActiveByShop(long shopId) {
		return null;
	}

	@Override
	public List<ShopSubscription> findShopSubscriptions(long shopId, long startAt) {
		var status = ShopSubscription.Status.SUCCESS;
		return shopSubscriptionRepo
				.findByShopIdAndStatusAndStartAtGreaterThanEqualOrderByCreatedAtDesc(shopId, status, startAt).stream()
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
		return PageDataMapper.map(pageResult, ShopSubscriptionMapper::toDomainCompat);
	}

}
