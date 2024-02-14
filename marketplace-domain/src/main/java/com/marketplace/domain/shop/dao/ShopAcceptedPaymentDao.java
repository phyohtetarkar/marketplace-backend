package com.marketplace.domain.shop.dao;

import java.util.List;

import com.marketplace.domain.shop.ShopAcceptedPayment;
import com.marketplace.domain.shop.ShopAcceptedPaymentInput;

public interface ShopAcceptedPaymentDao {

    void save(ShopAcceptedPaymentInput values);
    
    void saveAll(List<ShopAcceptedPaymentInput> payments);

    void delete(long id);

    List<ShopAcceptedPayment> findAllByShop(long shopId);

}
