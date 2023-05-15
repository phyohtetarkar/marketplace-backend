package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.product.Product;

public interface ProductSearchDao {

    long save(Product product);

    void delete(long productId);

    List<String> getSuggestions(String q, int limit);
}
