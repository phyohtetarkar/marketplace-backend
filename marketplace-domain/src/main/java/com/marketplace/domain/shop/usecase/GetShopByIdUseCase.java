package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class GetShopByIdUseCase {

	@Autowired
    private ShopDao dao;

    @Transactional(readOnly = true)
    public Shop apply(long id) {
        var shop = dao.findById(id);
        if (shop == null || shop.isDeleted()) {
        	throw ApplicationException.notFound("Shop not found");
        }
        return shop;
    }

}
