package com.shoppingcenter.data.discount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepo extends JpaRepository<DiscountEntity, Long> {

	Page<DiscountEntity> findByShopId(long shopId, Pageable pageable);

}
