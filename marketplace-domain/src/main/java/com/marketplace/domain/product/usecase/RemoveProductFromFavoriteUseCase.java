package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.product.dao.FavoriteProductDao;

@Component
public class RemoveProductFromFavoriteUseCase {

	@Autowired
    private FavoriteProductDao dao;

    public void apply(long userId, long productId) {
        dao.delete(userId, productId);
    }

}
