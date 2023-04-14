package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;

import lombok.Setter;

@Setter
public class DeleteShopAcceptedPaymentUseCaseImpl implements DeleteShopAcceptedPaymentUseCase {

    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

    @Override
    public void apply(long id) {
        shopAcceptedPaymentDao.delete(id);
    }

}
