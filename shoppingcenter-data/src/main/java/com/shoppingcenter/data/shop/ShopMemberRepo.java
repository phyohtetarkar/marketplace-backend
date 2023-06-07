package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcenter.domain.shop.ShopMember;

public interface ShopMemberRepo extends JpaRepository<ShopMemberEntity, ShopMemberEntity.ID> {

	List<ShopMemberEntity> findByShopIdAndRole(long shopId, ShopMember.Role role, Pageable pageable);

	Page<ShopMemberEntity> findByUserId(long userId, Pageable pageable);

	List<ShopMemberEntity> findByShopId(long shopId, Pageable pageable);

	void deleteByShopId(long shopId);
	
	long countByUserId(long userId);

}
