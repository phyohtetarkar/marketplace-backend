package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.Shop;

public class AddProductToFavoriteUseCase {

    private FavoriteProductDao favoriteProductDao;

    private ProductDao productDao;

    public AddProductToFavoriteUseCase(FavoriteProductDao favoriteProductDao, ProductDao productDao) {
        this.favoriteProductDao = favoriteProductDao;
        this.productDao = productDao;
    }

    public boolean apply(long userId, long productId) {
        var product = productDao.findById(productId);
        
        if (product == null || product.isDisabled() || product.getStatus() != Product.Status.PUBLISHED) {
        	throw new ApplicationException("Product not found");
        }
        
        var shop = product.getShop();
        
        if (shop.getStatus() != Shop.Status.APPROVED || shop.getExpiredAt() < System.currentTimeMillis()) {
        	throw new ApplicationException("Product not found");
        }

        if (favoriteProductDao.exists(userId, productId)) {
            return false;
        }

        favoriteProductDao.add(userId, productId);

        return true;
    }

}
