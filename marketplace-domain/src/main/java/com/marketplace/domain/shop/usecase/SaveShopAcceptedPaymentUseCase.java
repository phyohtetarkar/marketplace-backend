package com.marketplace.domain.shop.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.ShopAcceptedPaymentInput;
import com.marketplace.domain.shop.dao.ShopAcceptedPaymentDao;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class SaveShopAcceptedPaymentUseCase {

	@Autowired
    private ShopAcceptedPaymentDao shopAcceptedPaymentDao;

	@Autowired
    private ShopDao shopDao;

    public void apply(long shopId, List<ShopAcceptedPaymentInput> payments) {
        if (!shopDao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }
        
        payments.forEach(p -> {
        	p.setShopId(shopId);
        });

        shopAcceptedPaymentDao.saveAll(payments);
    }

}
