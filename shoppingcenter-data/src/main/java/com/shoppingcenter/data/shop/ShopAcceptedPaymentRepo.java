package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopAcceptedPaymentRepo extends JpaRepository<ShopAcceptedPaymentEntity, Long> {

    List<ShopAcceptedPaymentEntity> findByShopId(long shopId);

}
