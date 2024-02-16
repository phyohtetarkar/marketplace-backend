package com.marketplace.data.discount;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepo extends JpaRepository<DiscountEntity, Long> {
	
	List<DiscountEntity> findByShopId(long shopId);

	Page<DiscountEntity> findByShopId(long shopId, Pageable pageable);

}
