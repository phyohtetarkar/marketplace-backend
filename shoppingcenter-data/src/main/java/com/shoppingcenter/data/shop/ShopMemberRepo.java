package com.shoppingcenter.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopMemberRepo extends JpaRepository<ShopMemberEntity, String> {

	Page<ShopMemberEntity> findByShop_IdAndRole(long shopId, ShopMemberEntity.Role role, Pageable pageable);

	Page<ShopMemberEntity> findByUser_Id(String userId, Pageable pageable);

	Page<ShopMemberEntity> findByShop_Id(long shopId, Pageable pageable);

	void deleteByShop_Id(long shopId);

}
