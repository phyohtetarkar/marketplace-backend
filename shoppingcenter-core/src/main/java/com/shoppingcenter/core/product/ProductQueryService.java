package com.shoppingcenter.core.product;

import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.product.model.Product;

public interface ProductQueryService {

    Product findById(long id);

    Product findBySlug(String slug);

    PageResult<Product> findAll(ProductQuery query);

}
