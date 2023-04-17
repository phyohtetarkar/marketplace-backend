package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class AddProductToFavoriteUseCase {

    private FavoriteProductDao favoriteProductDao;

    private ProductDao productDao;

    public AddProductToFavoriteUseCase(FavoriteProductDao favoriteProductDao, ProductDao productDao) {
        this.favoriteProductDao = favoriteProductDao;
        this.productDao = productDao;
    }

    public boolean apply(long userId, long productId) {
        if (!productDao.isAvailable(productId)) {
            throw new ApplicationException("Product not found");
        }

        if (favoriteProductDao.exists(userId, productId)) {
            return false;
        }

        favoriteProductDao.add(userId, productId);

        return true;
    }

}
