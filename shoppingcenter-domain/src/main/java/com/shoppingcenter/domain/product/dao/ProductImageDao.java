package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.product.ProductImage;

public interface ProductImageDao {

    void saveAll(List<ProductImage> list);
    
    void deleteAll(List<Long> list);

}
