package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class UpdateProductStatusUseCase {

	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public void apply(long shopId, long productId, Product.Status status) {
		if (!productDao.existsByIdAndShop(productId, shopId)) {
			throw new ApplicationException("Product not found");
		}
		
		if (status == null) {
			throw new ApplicationException("Required product status");
		}
		
		productDao.updateStatus(productId, status);
	}
	
}
