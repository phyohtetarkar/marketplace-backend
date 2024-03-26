package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class GetProductBySlugUseCase {

	@Autowired
	private ProductDao dao;

	@Transactional(readOnly = true)
	public Product apply(String slug) {
		var product = dao.findBySlug(slug);
		if (product == null || product.isDeleted() || product.getStatus() != Product.Status.PUBLISHED) {
			throw ApplicationException.notFound("Product not found");
		}
		
//		if (!product.isWithVariant() && !product.isAvailable()) {
//			throw ApplicationException.notFound("Product not found");
//		}
		
		var shop = product.getShop();
		
		if (shop.getExpiredAt() < System.currentTimeMillis()) {
        	throw ApplicationException.notFound("Product not found");
        }
		return product;
	}

}
