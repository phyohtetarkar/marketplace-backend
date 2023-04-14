package com.shoppingcenter.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcenter.domain.shop.Shop;

public interface ShopMemberRepo extends JpaRepository<ShopMemberEntity, ShopMemberEntity.ID> {

	Page<ShopMemberEntity> findByShopIdAndRole(long shopId, String role, Pageable pageable);

	Page<ShopMemberEntity> findByUserIdAndShopStatus(long userId, Shop.Status status, Pageable pageable);

	Page<ShopMemberEntity> findByShopId(long shopId, Pageable pageable);

	void deleteByShopId(long shopId);

}
