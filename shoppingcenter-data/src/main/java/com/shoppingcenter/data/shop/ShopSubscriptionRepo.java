package com.shoppingcenter.data.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingcenter.domain.shop.ShopSubscription;

public interface ShopSubscriptionRepo extends JpaRepository<ShopSubscriptionEntity, Long>, JpaSpecificationExecutor<ShopSubscriptionEntity> {

	//Optional<ShopSubscriptionEntity> findByInvoiceNumber(String invoiceNo);
	
	Optional<ShopSubscriptionEntity> findByShopIdAndStatus(long shopId, ShopSubscription.Status status);

	//boolean existsByInvoiceNumber(String invoiceNo);
	
	//void deleteByInvoiceNumber(String invoiceNo);
	
	@Modifying
	@Query("UPDATE ShopSubscription ss SET ss.active = :active WHERE ss.id = :id")
	void updateActive(@Param("id") long id, @Param("active") boolean active);
	
	List<ShopSubscriptionEntity> findByShopIdAndStatusAndStartAtGreaterThanEqualOrderByCreatedAtDesc(long shopId, ShopSubscription.Status status,
			long startAt);
	
	Page<ShopSubscriptionEntity> findByStatusAndActiveTrue(ShopSubscription.Status status, Pageable pageable);
}
