package com.marketplace.domain.discount.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.discount.Discount;
import com.marketplace.domain.discount.DiscountDao;

@Component
public class GetDiscountsByShopUseCase {

	@Autowired
    private DiscountDao dao;

    public List<Discount> apply(long shopId) {
        return dao.findByShop(shopId);
    }

}
