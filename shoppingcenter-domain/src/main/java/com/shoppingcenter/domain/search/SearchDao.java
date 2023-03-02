package com.shoppingcenter.domain.search;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface SearchDao {

    void saveProduct(Product product);

    void saveAllProduct(List<Product> products);

    void deleteProduct(long productId);

    PageData<Product> getProducts(ProductQuery query);

}
