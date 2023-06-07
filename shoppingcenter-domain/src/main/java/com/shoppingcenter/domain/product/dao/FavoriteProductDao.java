package com.shoppingcenter.domain.product.dao;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;

public interface FavoriteProductDao {

    void add(long userId, long productId);

    void delete(long userId, long productId);

    void deleteByProduct(long productId);

    boolean exists(long userId, long productId);
    
    long getFavoriteCountByUser(long userId);

    PageData<Product> findByUser(long userId, int page);

}
