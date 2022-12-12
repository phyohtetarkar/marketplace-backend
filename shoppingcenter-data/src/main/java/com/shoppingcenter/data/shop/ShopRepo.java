package com.shoppingcenter.data.shop;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shoppingcenter.data.shop.ShopEntity.Status;

public interface ShopRepo extends JpaRepository<ShopEntity, Long>, JpaSpecificationExecutor<ShopEntity> {

	Optional<ShopEntity> findBySlug(String slug);
	
	long countByStatusAndCreatedAt(Status status, long createdAt);
	
}
