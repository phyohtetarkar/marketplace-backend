package com.shoppingcenter.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopMemberRepo extends JpaRepository<ShopMemberEntity, ShopMemberEntity.Id> {
	
	Page<ShopMemberEntity> findByUserId(String userId, Pageable pageable);

	Page<ShopMemberEntity> findByShopId(long shopId, Pageable pageable);
	
	void deleteByShopId(long shopId);
	
}
