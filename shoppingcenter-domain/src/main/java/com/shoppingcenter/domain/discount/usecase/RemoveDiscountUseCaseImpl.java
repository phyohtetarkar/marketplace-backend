package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.discount.DiscountDao;

public class RemoveDiscountUseCaseImpl implements RemoveDiscountUseCase {

    private DiscountDao dao;

    public RemoveDiscountUseCaseImpl(DiscountDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(long discountId, Long productId) {
        if (!dao.existsById(discountId)) {
            throw new ApplicationException("Discount not found");
        }
        dao.removeDiscount(discountId, productId);
    }

}
