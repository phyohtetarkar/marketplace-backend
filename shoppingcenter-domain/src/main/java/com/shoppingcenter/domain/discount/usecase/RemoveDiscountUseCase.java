package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.discount.DiscountDao;

public class RemoveDiscountUseCase {

    private DiscountDao dao;

    public RemoveDiscountUseCase(DiscountDao dao) {
        this.dao = dao;
    }

    public void apply(long discountId, Long productId) {
        if (!dao.existsById(discountId)) {
            throw new ApplicationException("Discount not found");
        }
        dao.removeDiscount(discountId, productId);
    }

}
