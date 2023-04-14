package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

import com.shoppingcenter.domain.shop.ShopAcceptedPayment;
import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;

public class GallAllShopAcceptedPaymentUseCaseImpl implements GallAllShopAcceptedPaymentUseCase {

    private ShopAcceptedPaymentDao dao;

    public GallAllShopAcceptedPaymentUseCaseImpl(ShopAcceptedPaymentDao dao) {
        this.dao = dao;
    }

    @Override
    public List<ShopAcceptedPayment> apply(long shopId) {
        return dao.findAllByShop(shopId);
    }

}
