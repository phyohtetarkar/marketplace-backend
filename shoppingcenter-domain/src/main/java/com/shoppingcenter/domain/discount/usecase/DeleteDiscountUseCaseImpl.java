package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.discount.DiscountDao;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class DeleteDiscountUseCaseImpl implements DeleteDiscountUseCase {

    private DiscountDao discountDao;

    private ProductDao productDao;

    public DeleteDiscountUseCaseImpl(DiscountDao discountDao, ProductDao productDao) {
        this.discountDao = discountDao;
        this.productDao = productDao;
    }

    @Override
    public void apply(long id) {
        if (!discountDao.existsById(id)) {
            throw new ApplicationException("Discount not found");
        }
        if (productDao.countByDiscount(id) > 0) {
            productDao.removeDiscount(id);
        }
        discountDao.delete(id);
    }

}
