package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.product.ProductImage;

public interface ProductImageDao {

    void save(ProductImage image);

    void saveAll(List<ProductImage> list);

    void delete(ProductImage image);

    void deleteAll(List<ProductImage> list);

    List<ProductImage> findByProduct(long productId);
}
