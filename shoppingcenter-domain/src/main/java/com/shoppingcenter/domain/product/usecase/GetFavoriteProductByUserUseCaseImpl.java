package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

public class GetFavoriteProductByUserUseCaseImpl implements GetFavoriteProductByUserUseCase {

    private FavoriteProductDao dao;

    public GetFavoriteProductByUserUseCaseImpl(FavoriteProductDao dao) {
        this.dao = dao;
    }

    @Override
    public PageData<Product> apply(String userId, Integer page) {
        return dao.findByUser(userId, Utils.normalizePage(page));
    }

}
