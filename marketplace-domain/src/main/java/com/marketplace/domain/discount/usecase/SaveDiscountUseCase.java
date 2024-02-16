package com.marketplace.domain.discount.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.discount.DiscountDao;
import com.marketplace.domain.discount.DiscountInput;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class SaveDiscountUseCase {

	@Autowired
    private DiscountDao discountDao;

	@Autowired
    private ShopDao shopDao;

	@Transactional
    public void apply(DiscountInput values) {
        if (!Utils.hasText(values.getTitle())) {
            throw new ApplicationException("Required discount title");
        }

        if (!shopDao.existsById(values.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        discountDao.save(values);
    }

}
