package com.marketplace.domain.product.dao;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.product.Product;

public interface FavoriteProductDao {

    void add(long userId, long productId);

    void delete(long userId, long productId);

    void deleteByProduct(long productId);

    boolean exists(long userId, long productId);
    
    long getFavoriteCountByUser(long userId);

    PageData<Product> findByUser(long userId, PageQuery pageQuery);

}
