package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.dao.ShopAcceptedPaymentDao;

@Component
public class DeleteShopAcceptedPaymentUseCase {

	@Autowired
    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

    public void apply(long id) {
        shopAcceptedPaymentDao.delete(id);
    }

}
