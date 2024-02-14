package com.marketplace.domain.discount.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.discount.DiscountDao;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class DeleteDiscountUseCase {

	@Autowired
    private DiscountDao discountDao;

	@Autowired
    private ProductDao productDao;

	@Transactional
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
