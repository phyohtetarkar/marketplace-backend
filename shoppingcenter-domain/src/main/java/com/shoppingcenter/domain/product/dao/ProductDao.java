package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface ProductDao {

    long save(Product product);

    void delete(long id);

    void removeDiscount(long discountId);

    boolean existsById(long id);

    boolean existsByIdAndStatus(long id, Product.Status status);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndStatus(String slug, Product.Status status);

    boolean existsByCategoryId(int categoryId);

    long countByIdNotAndCategory(long productId, int categoryId);

    long countByDiscount(long discountId);

    long countByShop(long shopId);

    Product findById(long id);

    Product findBySlug(String slug);

    Product.Status getProductStatus(long id);

    List<Product> findProductHints(String q, int limit);

    List<String> findProductBrandsByCategory(String categorySlug);

    List<String> findProductBrandsByCategoryId(int categoryId);

    List<Product> getRelatedProducts(long productId, PageQuery pageQuery);

    PageData<Product> getProducts(ProductQuery query);
}
