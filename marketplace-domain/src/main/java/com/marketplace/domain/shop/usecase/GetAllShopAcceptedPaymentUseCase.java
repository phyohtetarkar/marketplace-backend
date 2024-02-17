package com.marketplace.domain.shop.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.ShopAcceptedPayment;
import com.marketplace.domain.shop.dao.ShopAcceptedPaymentDao;

@Component
public class GetAllShopAcceptedPaymentUseCase {

	@Autowired
    private ShopAcceptedPaymentDao dao;

    public List<ShopAcceptedPayment> apply(long shopId) {
        return dao.findAllByShop(shopId);
    }

}
