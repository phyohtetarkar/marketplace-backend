package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;

import lombok.Setter;

@Setter
public class DeleteShopAcceptedPaymentUseCase {

    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

    public void apply(long id) {
        shopAcceptedPaymentDao.delete(id);
    }

}
