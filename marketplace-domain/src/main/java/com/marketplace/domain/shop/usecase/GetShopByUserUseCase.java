package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class GetShopByUserUseCase {

	@Autowired
    private ShopDao dao;

	@Transactional(readOnly = true)
    public PageData<Shop> apply(long userId, Integer page) {
        return dao.findByUser(userId, PageQuery.of(page, Constants.PAGE_SIZE));
    }

}
