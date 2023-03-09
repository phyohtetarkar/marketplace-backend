package com.shoppingcenter.domain.product.dao;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.FavoriteProduct;

public interface FavoriteProductDao {

    void add(String userId, long productId);

    void delete(long id);

    void deleteByProduct(long productId);

    boolean existsByUserAndProduct(String userId, long productId);

    PageData<FavoriteProduct> findByUser(String userId, int page);

}
