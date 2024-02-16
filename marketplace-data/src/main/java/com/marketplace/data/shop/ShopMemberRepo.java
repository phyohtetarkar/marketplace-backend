package com.marketplace.data.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.domain.shop.ShopMember;

public interface ShopMemberRepo extends JpaRepository<ShopMemberEntity, ShopMemberEntity.ID> {

	List<ShopMemberEntity> findByShopIdAndRole(long shopId, ShopMember.Role role, Pageable pageable);

	Page<ShopMemberEntity> findByUserIdAndShop_DeletedFalse(long userId, Pageable pageable);

	List<ShopMemberEntity> findByShopId(long shopId);

	void deleteByShopId(long shopId);
	
	long countByUserId(long userId);

}
