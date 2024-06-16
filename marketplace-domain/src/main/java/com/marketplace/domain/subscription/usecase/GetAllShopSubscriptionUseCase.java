package com.marketplace.domain.subscription.usecase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchCriteria;
import com.marketplace.domain.common.SearchCriteria.Operator;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.ShopSubscriptionQuery;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDao;

@Component
public class GetAllShopSubscriptionUseCase {

	@Autowired
	private ShopSubscriptionDao shopSubscriptionDao;

	@Transactional(readOnly = true)
	public PageData<ShopSubscription> apply(ShopSubscriptionQuery query) {
		var sq = new SearchQuery();
		ZoneId zoneId = ZoneOffset.UTC;
		
		try {
			zoneId = StringUtils.hasText(query.getTimeZone()) ? ZoneId.of(query.getTimeZone()): ZoneOffset.UTC;
		} catch (Exception e) {
		}
		
		if (query.getShopId() != null && query.getShopId() > 0) {
            var c = SearchCriteria.simple("shop.id", Operator.EQUAL, query.getShopId());
			sq.addCriteria(c);
		}
		
		if (query.getStatus() != null) {
			var c = SearchCriteria.simple("status", Operator.EQUAL, query.getStatus());
			sq.addCriteria(c);
		} else {
			var c = SearchCriteria.notNull("status");
			sq.addCriteria(c);
		}
		
		if (StringUtils.hasText(query.getFromDate())) {
			var date = LocalDate.parse(query.getFromDate());
			
			var from = date.atStartOfDay(zoneId).toInstant().toEpochMilli();
			
			var c = SearchCriteria.simple("createdAt", Operator.GREATER_THAN_EQ, from);
			sq.addCriteria(c);
		}
		
		if (StringUtils.hasText(query.getToDate())) {
			var date = LocalDate.parse(query.getToDate());
			
			var to = date.atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli();
			
			var c = SearchCriteria.simple("createdAt", Operator.LESS_THAN_EQ, to);
			sq.addCriteria(c);
		}
		
		sq.setPageQuery(PageQuery.of(query.getPage(), Constants.PAGE_SIZE));
		
		return shopSubscriptionDao.findAll(sq);
	}

}
