package com.shoppingcenter.data.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopMemberRepo extends JpaRepository<ShopMemberEntity, ShopMemberEntity.ID> {

	Page<ShopMemberEntity> findByShopIdAndRole(long shopId, String role, Pageable pageable);

	Page<ShopMemberEntity> findByUserIdAndShopActivatedTrue(long userId, Pageable pageable);

	Page<ShopMemberEntity> findByShopId(long shopId, Pageable pageable);

	void deleteByShopId(long shopId);

}
