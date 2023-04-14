package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

public class RemoveProductFromFavoriteUseCaseImpl implements RemoveProductFromFavoriteUseCase {

    private FavoriteProductDao dao;

    public RemoveProductFromFavoriteUseCaseImpl(FavoriteProductDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(long userId, long productId) {
        dao.delete(userId, productId);
    }

}
