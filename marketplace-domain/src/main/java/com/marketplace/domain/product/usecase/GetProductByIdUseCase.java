package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class GetProductByIdUseCase {

	@Autowired
	private ProductDao dao;

	public GetProductByIdUseCase(ProductDao dao) {
		this.dao = dao;
	}

	@Transactional(readOnly = true)
	public Product apply(long id) {
		var product = dao.findById(id);
		if (product == null || product.isDeleted()) {
			throw ApplicationException.notFound("Product not found");
		}
		return product;
	}

}
