package com.marketplace.domain.subscription.dao;

import com.marketplace.domain.subscription.ShopSubscriptionTransaction;

public interface ShopSubscriptionTransactionDao {

	void save(ShopSubscriptionTransaction trans);

	ShopSubscriptionTransaction findByInvoiceNo(long invoiceNo);
}
