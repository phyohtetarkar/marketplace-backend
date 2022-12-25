package com.shoppingcenter.core.product;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.product.model.Product;

public interface ProductQueryService {

    Product findById(long id);

    Product findBySlug(String slug);

    PageData<Product> findAll(ProductQuery query);

}
