package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.product.ProductVariant;

public interface ProductVariantDao {

    void save(ProductVariant variant);

    void saveAll(List<ProductVariant> list);

    void delete(ProductVariant variant);

    void deleteAll(List<ProductVariant> list);

    boolean exists(long id);

}
