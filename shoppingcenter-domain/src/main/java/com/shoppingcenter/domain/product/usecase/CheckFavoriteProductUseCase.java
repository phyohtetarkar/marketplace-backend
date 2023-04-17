package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

public class CheckFavoriteProductUseCase {

    private FavoriteProductDao dao;

    public CheckFavoriteProductUseCase(FavoriteProductDao dao) {
        this.dao = dao;
    }

    public boolean apply(long userId, long productId) {
        return dao.exists(userId, productId);
    }

}
