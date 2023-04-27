package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopAcceptedPayment;
import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class SaveShopAcceptedPaymentUseCase {

    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

    private ShopDao shopDao;

    public void apply(long shopId, List<ShopAcceptedPayment> payments) {
        if (!shopDao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }
        
        payments.forEach(p -> {
        	p.setShopId(shopId);
        });

        shopAcceptedPaymentDao.saveAll(payments);
    }

}
