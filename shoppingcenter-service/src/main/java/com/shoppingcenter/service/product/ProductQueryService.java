package com.shoppingcenter.service.product;

import java.util.List;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.product.model.Product;

public interface ProductQueryService {

    Product findById(long id);

    Product findBySlug(String slug);

    List<Product> getHints(String q);

    List<String> findProductBrandsByCategory(String categorySlug);

    PageData<Product> findAll(ProductQuery query);

}
