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

	Optional<ShopSubscriptionEntity> findByShopIdAndStatus(long shopId, ShopSubscription.Status status);
	
	Optional<ShopSubscriptionEntity> findByShopIdAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(long shopId, ShopSubscription.Status status, long startAt, long endAt);
	
	Optional<ShopSubscriptionEntity> findTopByShopIdAndStatusOrderByStartAtDesc(long shopId, ShopSubscription.Status status);
	
	List<ShopSubscriptionEntity> findByShopIdAndStatusAndPreSubscriptionTrueOrderByStartAtDesc(long shopId, ShopSubscription.Status status);
	
	Page<ShopSubscriptionEntity> findByStatus(ShopSubscription.Status status, Pageable pageable);
	
	@Modifying
	@Query("UPDATE ShopSubscription ss SET ss.status = :status WHERE ss.id = :id")
	void updateStatus(@Param("id") long id, @Param("status") ShopSubscription.Status status);
}
