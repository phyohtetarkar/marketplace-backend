package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.product.dao.ProductDao;

@Component
public class UpdateProductFeaturedUseCase {

	@Autowired
	private ProductDao dao;
	
	@Transactional
	public void apply(long productId, boolean featured) {
		if (!dao.existsById(productId)) {
			throw new ApplicationContextException("Product not found");
		}
		dao.updateFeatured(productId, featured);
	}
	
}
