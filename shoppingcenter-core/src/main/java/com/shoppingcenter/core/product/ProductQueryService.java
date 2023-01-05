package com.shoppingcenter.core.product;

import java.util.List;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.product.model.Product;

public interface ProductQueryService {

    Product findById(long id);

    Product findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Product> getHints(String q);

    PageData<Product> findAll(ProductQuery query);

}
