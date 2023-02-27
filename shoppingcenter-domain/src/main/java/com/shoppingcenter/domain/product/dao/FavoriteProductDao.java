package com.shoppingcenter.domain.product.dao;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;

public interface FavoriteProductDao {

    void add(String userId, long productId);

    void delete(long id);

    void deleteByProduct(long productId);

    boolean existsByUserAndProduct(String userId, long productId);

    PageData<Product> findByUser(String userId, int page);

}
