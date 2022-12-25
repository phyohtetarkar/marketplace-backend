package com.shoppingcenter.data.shop;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopContactRepo extends JpaRepository<ShopContactEntity, Long> {

    Optional<ShopContactEntity> findByShop_Id(long shopId);
}
