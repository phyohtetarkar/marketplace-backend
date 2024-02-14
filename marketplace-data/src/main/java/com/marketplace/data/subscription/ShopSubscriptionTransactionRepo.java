package com.marketplace.data.subscription;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopSubscriptionTransactionRepo extends JpaRepository<ShopSubscriptionTransactionEntity, Long> {

	Optional<ShopSubscriptionTransactionEntity> findByInvoiceNo(long invoiceNo);
}
