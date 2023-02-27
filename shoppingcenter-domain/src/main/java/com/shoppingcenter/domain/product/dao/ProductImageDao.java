package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.product.ProductImage;

public interface ProductImageDao {

    long save(ProductImage image);

    void delete(long id);

    List<ProductImage> findByProduct(long productId);
}
