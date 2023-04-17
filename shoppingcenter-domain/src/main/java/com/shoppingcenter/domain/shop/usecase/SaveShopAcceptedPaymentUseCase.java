package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopAcceptedPayment;
import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class SaveShopAcceptedPaymentUseCase {

    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

    private ShopDao shopDao;

    public void apply(ShopAcceptedPayment payment) {
        if (!shopDao.existsById(payment.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        shopAcceptedPaymentDao.save(payment);
    }

}
