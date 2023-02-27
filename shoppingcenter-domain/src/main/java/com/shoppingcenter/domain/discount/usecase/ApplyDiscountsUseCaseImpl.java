package com.shoppingcenter.domain.discount.usecase;

import java.util.List;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.discount.DiscountDao;

public class ApplyDiscountsUseCaseImpl implements ApplyDiscountsUseCase {

    private DiscountDao dao;

    public ApplyDiscountsUseCaseImpl(DiscountDao dao) {
        this.dao = dao;
    }

    @Override
    public void applyDiscounts(long discountId, List<Long> productIds) {
        if (!dao.existsById(discountId)) {
            throw new ApplicationException("Discount not found");
        }
        dao.applyDiscounts(discountId, productIds);
    }

}
