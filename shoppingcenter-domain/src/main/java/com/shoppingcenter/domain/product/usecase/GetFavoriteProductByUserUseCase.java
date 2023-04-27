package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;

public class GetFavoriteProductByUserUseCase {

    private FavoriteProductDao dao;

    public GetFavoriteProductByUserUseCase(FavoriteProductDao dao) {
        this.dao = dao;
    }

    public PageData<Product> apply(long userId, Integer page) {
        return dao.findByUser(userId, Utils.normalizePage(page));
    }

}
