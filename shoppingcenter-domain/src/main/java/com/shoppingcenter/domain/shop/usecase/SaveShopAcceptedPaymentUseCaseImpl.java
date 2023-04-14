package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopAcceptedPayment;
import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class SaveShopAcceptedPaymentUseCaseImpl implements SaveShopAcceptedPaymentUseCase {

    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

    private ShopDao shopDao;

    @Override
    public void apply(ShopAcceptedPayment payment) {
        if (payment.getShopId() == null || !shopDao.existsById(payment.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        shopAcceptedPaymentDao.save(payment);
    }

}
