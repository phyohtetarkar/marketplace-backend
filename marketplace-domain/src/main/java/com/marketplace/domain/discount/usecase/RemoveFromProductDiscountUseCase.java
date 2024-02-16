package com.marketplace.domain.discount.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.discount.DiscountDao;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class RemoveFromProductDiscountUseCase {

	@Autowired
    private DiscountDao discountDao;
	
	@Autowired
	private ProductDao productDao;

	@Transactional
    public void apply(long shopId, long productId) {
        if (!productDao.existsByIdAndShop(productId, shopId)) {
            throw new ApplicationException("Product not found");
        }
        discountDao.removeDiscountFromProduct(productId);
    }

}
