package com.marketplace.data.subscription;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriptionPromoRepo extends JpaRepository<SubscriptionPromoEntity, Long>, JpaSpecificationExecutor<SubscriptionPromoEntity> {
	
	Optional<SubscriptionPromoEntity> findByCode(String code);
	
	boolean existsByCode(String code);
	
	Page<SubscriptionPromoEntity> findByUsedTrue(Pageable pageable);
	
	Page<SubscriptionPromoEntity> findByExpiredAtLessThan(long currentTime, Pageable pageable);
	
	Page<SubscriptionPromoEntity> findByUsedFalseAndExpiredAtGreaterThanEqual(long currentTime, Pageable pageable);
	
	@Modifying
	@Query("UPDATE SubscriptionPromo sp SET sp.used = :used WHERE sp.id = :id")
	void updateUsed(@Param("id") long id, @Param("used") boolean used);
	
}
