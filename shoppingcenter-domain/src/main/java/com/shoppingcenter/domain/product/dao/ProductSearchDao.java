package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface ProductSearchDao {

    String save(Product product);

    void delete(long productId);

    void setDiscount(List<Long> productIds, Discount discount);

    List<String> getProductBrands(String categorySlug);

    List<Product> getHints(String q, int limit);

    PageData<Product> getProducts(ProductQuery query);

}
