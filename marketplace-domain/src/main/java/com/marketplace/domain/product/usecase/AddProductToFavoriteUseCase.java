package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.FavoriteProductDao;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class AddProductToFavoriteUseCase {

	@Autowired
    private FavoriteProductDao favoriteProductDao;

	@Autowired
    private ProductDao productDao;

	@Transactional
    public boolean apply(long userId, long productId) {
        var product = productDao.findById(productId);
        
        if (product == null || product.isDeleted() || product.getStatus() != Product.Status.PUBLISHED) {
        	throw new ApplicationException("Product not found");
        }

        if (favoriteProductDao.exists(userId, productId)) {
            return false;
        }

        favoriteProductDao.add(userId, productId);

        return true;
    }

}
