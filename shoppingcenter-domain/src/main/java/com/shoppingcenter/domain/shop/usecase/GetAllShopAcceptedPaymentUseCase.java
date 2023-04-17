package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

import com.shoppingcenter.domain.shop.ShopAcceptedPayment;
import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;

public class GetAllShopAcceptedPaymentUseCase {

    private ShopAcceptedPaymentDao dao;

    public GetAllShopAcceptedPaymentUseCase(ShopAcceptedPaymentDao dao) {
        this.dao = dao;
    }

    public List<ShopAcceptedPayment> apply(long shopId) {
        return dao.findAllByShop(shopId);
    }

}
