package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.product.ProductFilter;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class GetProductFilterByCategoryUseCase {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;

	@Transactional(readOnly = true)
	public ProductFilter apply(int categoryId) {
		var filter = new ProductFilter();
		var category = categoryDao.findById(categoryId);
    	if (category == null) {
    		return filter;
    	}
    	
    	var lft = category.getLft();        
    	var rgt = category.getRgt();
    	var brands = productDao.findProductBrandsByCategory(lft, rgt);
        var maxPrice = productDao.getMaxPriceByCategory(lft, rgt);
        
        filter.setBrands(brands);     
        filter.setMaxPrice(maxPrice);
    	
    	return filter;
	}
	
}
