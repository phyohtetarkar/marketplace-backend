package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.FavoriteProductDao;

@Component
public class GetFavoriteProductByUserUseCase {

	@Autowired
    private FavoriteProductDao dao;

	@Transactional(readOnly = true)
    public PageData<Product> apply(long userId, Integer page) {
        return dao.findByUser(userId, PageQuery.of(page, Constants.PAGE_SIZE));
    }

}
