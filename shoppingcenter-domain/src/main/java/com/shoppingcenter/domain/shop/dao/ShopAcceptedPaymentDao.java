package com.shoppingcenter.domain.shop.dao;

import java.util.List;

import com.shoppingcenter.domain.shop.ShopAcceptedPayment;

public interface ShopAcceptedPaymentDao {

    void save(ShopAcceptedPayment payment);
    
    void saveAll(List<ShopAcceptedPayment> payments);

    void delete(long id);

    List<ShopAcceptedPayment> findAllByShop(long shopId);

}
