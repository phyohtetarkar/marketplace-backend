package com.shoppingcenter.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopMemberRepo extends JpaRepository<ShopMemberEntity, Long> {

	Page<ShopMemberEntity> findByShop_IdAndRole(long shopId, String role, Pageable pageable);

	Page<ShopMemberEntity> findByUser_IdAndShopStatusNot(String userId, String status, Pageable pageable);

	Page<ShopMemberEntity> findByShop_Id(long shopId, Pageable pageable);

	boolean existsByShop_IdAndUser_Id(long shopId, String userId);

	void deleteByShop_Id(long shopId);

}
