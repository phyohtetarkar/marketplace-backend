package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

public class RemoveProductFromFavoriteUseCase {

    private FavoriteProductDao dao;

    public RemoveProductFromFavoriteUseCase(FavoriteProductDao dao) {
        this.dao = dao;
    }

    public void apply(long userId, long productId) {
        dao.delete(userId, productId);
    }

}
