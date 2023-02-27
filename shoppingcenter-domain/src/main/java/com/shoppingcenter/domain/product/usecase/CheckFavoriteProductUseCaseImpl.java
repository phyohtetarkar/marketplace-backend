package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

public class CheckFavoriteProductUseCaseImpl implements CheckFavoriteProductUseCase {

    private FavoriteProductDao dao;

    public CheckFavoriteProductUseCaseImpl(FavoriteProductDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean apply(String userId, long productId) {
        return dao.existsByUserAndProduct(userId, productId);
    }

}
