package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.discount.DiscountDao;
import com.shoppingcenter.domain.product.dao.ProductDao;

import lombok.Setter;

@Setter
public class DeleteDiscountUseCaseImpl implements DeleteDiscountUseCase {

    private DiscountDao discountDao;

    private ProductDao productDao;

    @Override
    public void apply(long id) {
        var discount = discountDao.findById(id);
        if (discount == null) {
            throw new ApplicationException("Discount not found");
        }
        if (productDao.countByDiscount(id) > 0) {
            productDao.removeDiscount(id);
        }
        discountDao.delete(id);
    }

}
