package com.marketplace.domain.general.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.banner.BannerDao;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.common.SortQuery;
import com.marketplace.domain.general.HomeData;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class GetHomePageDataUseCase {

	@Autowired
	private BannerDao bannerDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Transactional(readOnly = true)
	public HomeData apply() {
		var data = new HomeData();
		var sort = SortQuery.desc("createdAt");
		data.setBanners(bannerDao.findAll(sort));
		data.setMainCategories(categoryDao.findRootCategories());
		data.setFeaturedProducts(productDao.getTopFeaturedProducts());
		data.setDiscountProducts(productDao.getTopDiscountProducts());
		return data;
	}
	
}
