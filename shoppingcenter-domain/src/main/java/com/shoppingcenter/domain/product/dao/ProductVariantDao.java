package com.shoppingcenter.domain.product.dao;

import com.shoppingcenter.domain.product.ProductVariant;

public interface ProductVariantDao {

    long save(ProductVariant variant);

    void delete(long id);

    boolean existsById(long id);

}
