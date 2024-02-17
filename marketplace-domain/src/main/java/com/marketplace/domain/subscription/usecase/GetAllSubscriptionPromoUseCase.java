package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.subscription.SubscriptionPromo;
import com.marketplace.domain.subscription.SubscriptionPromoQuery;
import com.marketplace.domain.subscription.dao.SubscriptionPromoDao;

@Component
public class GetAllSubscriptionPromoUseCase {

	@Autowired
	private SubscriptionPromoDao subscriptionPromoDao;
	
	public PageData<SubscriptionPromo> apply(SubscriptionPromoQuery query) {
		var pageQuery = PageQuery.of(query.getPage(), Constants.PAGE_SIZE);
		if (query.getAvailable() == Boolean.TRUE) {
			return subscriptionPromoDao.findAvailable(pageQuery);
		}

		if (query.getUsed() == Boolean.TRUE) {
			return subscriptionPromoDao.findUsed(pageQuery);
		}

		if (query.getExpired() == Boolean.TRUE) {
			return subscriptionPromoDao.findExpired(pageQuery);
		}

		return subscriptionPromoDao.findAll(pageQuery);
	}
	
}
