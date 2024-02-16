package com.marketplace.domain.subscription.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.subscription.ShopSubscriptionTransaction;
import com.marketplace.domain.subscription.dao.ShopSubscriptionTransactionDao;

@Component
public class GetShopSubscriptionTransactionUseCase {

	@Autowired
	private ShopSubscriptionTransactionDao dao;

	public ShopSubscriptionTransaction apply(long invoiceNo) {
		var result = dao.findByInvoiceNo(invoiceNo);
//		if (result == null) {
//			throw ApplicationException.notFound("Subscription transaction not found");
//		}

		return result;
	}

}
