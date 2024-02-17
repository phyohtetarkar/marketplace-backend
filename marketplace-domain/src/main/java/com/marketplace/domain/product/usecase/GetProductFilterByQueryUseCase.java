package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.Utils;
import com.marketplace.domain.product.ProductFilter;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class GetProductFilterByQueryUseCase {
	
	@Autowired
	private ProductDao productDao;

	public ProductFilter apply(String q) {
		var filter = new ProductFilter();
    	if (Utils.hasText(q)) {
    		var query = "%" + q.trim() + "%";
    		filter.setBrands(productDao.findProductBrandsByQuery(query));
    		filter.setMaxPrice(productDao.getMaxPriceByNameLike(query));
    	};
    	
    	return filter;
	}
	
}
