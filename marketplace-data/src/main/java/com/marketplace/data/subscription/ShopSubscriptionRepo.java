package com.marketplace.data.subscription;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.marketplace.domain.subscription.ShopSubscription;

public interface ShopSubscriptionRepo extends JpaRepository<ShopSubscriptionEntity, Long>, JpaSpecificationExecutor<ShopSubscriptionEntity> {

	boolean existsByShopIdAndStatusAndTime_Id_StartAt(long shopId, ShopSubscription.Status status, long startAt);
	
	Optional<ShopSubscriptionEntity> findByInvoiceNo(long invoiceNo);
	
	Optional<ShopSubscriptionEntity> findByShopIdAndStatus(long shopId, ShopSubscription.Status status);
	
	Optional<ShopSubscriptionEntity> findByShopIdAndStatusAndTime_Id_StartAtLessThanEqualAndTime_Id_EndAtGreaterThanEqual(long shopId, ShopSubscription.Status status, long startAt, long endAt);
	
	Optional<ShopSubscriptionEntity> findTopByShopIdAndStatusOrderByTime_Id_StartAtDesc(long shopId, ShopSubscription.Status status);
	
	List<ShopSubscriptionEntity> findByShopIdAndStatusAndTime_Id_StartAtGreaterThanOrderByTime_Id_StartAtDesc(long shopId, ShopSubscription.Status status, long startAt);
	
	Page<ShopSubscriptionEntity> findByStatus(ShopSubscription.Status status, Pageable pageable);
	
	List<ShopSubscriptionEntity> findTop10ByStatusOrderByModifiedAtDesc(ShopSubscription.Status status);
	
	@Query(value = "SELECT COALESCE(SUM(ss.totalPrice), 0.0) FROM ShopSubscription ss WHERE ss.status = 'SUCCESS'")
	BigDecimal getTotalSubscriptionPrice();
}
