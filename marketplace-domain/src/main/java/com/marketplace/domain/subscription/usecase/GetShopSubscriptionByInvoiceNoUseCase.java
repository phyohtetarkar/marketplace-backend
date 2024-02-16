package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDao;

@Component
public class GetShopSubscriptionByInvoiceNoUseCase {

	@Autowired
	private ShopSubscriptionDao shopSubscriptionDao;

	@Transactional(readOnly = true)
	public ShopSubscription apply(long invoiceNo) {
		var result = shopSubscriptionDao.findByInvoiceNo(invoiceNo);
		if (result == null || result.getStatus() == null) {
			throw ApplicationException.notFound("Subscription not found");
		}

		return result;
	}

}
