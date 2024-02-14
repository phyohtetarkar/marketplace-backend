package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class GetShopBySlugUseCase {

	@Autowired
    private ShopDao dao;
	
    @Transactional(readOnly = true)
    public Shop apply(String slug) {
        var shop = dao.findBySlug(slug);
        if (shop == null || shop.isDeleted() || shop.getStatus() != Shop.Status.APPROVED) {
        	throw ApplicationException.notFound("Shop not found");
        }
        
        if (shop.getExpiredAt() < System.currentTimeMillis()) {
        	throw ApplicationException.notFound("Shop not found");
        }
        return shop;
    }

}
