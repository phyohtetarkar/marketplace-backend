package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class UpdateShopFeaturedUseCase {
	
	@Autowired
	private ShopDao dao;
	
	@Transactional
	public void apply(long shopId, boolean value) {
		if (!dao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}
		
		dao.updateFeatured(shopId, value);
	}
}
