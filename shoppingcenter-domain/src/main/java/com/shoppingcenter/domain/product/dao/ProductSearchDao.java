package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface ProductSearchDao {

    long save(Product product);

    void delete(long productId);

    List<String> getProductBrands(String categorySlug);

    List<Product> getHints(String q, int limit);

    List<Product> getRelatedProducts(long productId, int limit);

    PageData<Product> getProducts(ProductQuery query);

}
