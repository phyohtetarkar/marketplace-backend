package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.common.HTMLStringSanitizer;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class UpdateProductDescriptionUseCase {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private HTMLStringSanitizer htmlStringSanitizer;
	
	@Transactional
	public void apply(long shopId, long productId, String value) {
		if (!productDao.existsByIdAndShop(productId, shopId)) {
			throw new ApplicationException("Product not found");
		}
		
		var desc = htmlStringSanitizer.sanitize(value);
		
		productDao.updateDescription(productId, desc);
	}
	
}
