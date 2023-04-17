package com.shoppingcenter.domain.discount.usecase;

import java.util.List;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.discount.DiscountDao;

public class ApplyDiscountsUseCase {

    private DiscountDao dao;

    public ApplyDiscountsUseCase(DiscountDao dao) {
        this.dao = dao;
    }

    public void apply(long discountId, List<Long> productIds) {
        var discount = dao.findById(discountId);

        if (discount == null) {
            throw new ApplicationException("Discount not found");
        }
        dao.applyDiscounts(discountId, productIds);
    }

}
