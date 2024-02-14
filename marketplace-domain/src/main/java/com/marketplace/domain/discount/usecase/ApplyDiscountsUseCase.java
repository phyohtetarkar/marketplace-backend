package com.marketplace.domain.discount.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.discount.DiscountDao;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class ApplyDiscountsUseCase {

	@Autowired
    private DiscountDao discountDao;
	
	@Autowired
	private ProductDao productDao;

	@Transactional
    public void apply(long shopId, long discountId, List<Long> productIds) {
    	var discount = discountDao.findById(discountId);
        if (discount == null) {
            throw new ApplicationException("Discount not found");
        }
        
        if (shopId != discount.getShopId()) {
        	throw new ApplicationException("Discount not found");
        }
        
        var list = productIds.stream().filter(v -> v != null && v > 0).toList();
        
        for (long productId : list) {
			if (!productDao.existsByIdAndShop(productId, shopId)) {
				throw new ApplicationException("Unable to apply discount");
			}
		}
        
        discountDao.applyDiscounts(discountId, list);
    }

}
