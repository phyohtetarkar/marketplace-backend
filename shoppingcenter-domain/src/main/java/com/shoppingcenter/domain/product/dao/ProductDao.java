package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface ProductDao {

    long save(Product product);

    void updateThumbnail(long id, String thumbnail);
    
    void updateStockLeft(long id, int stockLeft);

    void delete(long id);

    void removeDiscount(long discountId);

    void toggleDisabled(long id, boolean disabled);

    void toggleHidden(long id, boolean hidden);

    boolean existsById(long id);

    boolean isAvailable(long id);

    boolean existsBySlug(String slug);

    boolean existsByCategoryId(int categoryId);

    long countByDiscount(long discountId);

    long countByShop(long shopId);

    Product findById(long id);

    Product findBySlug(String slug);

    List<Product> findProductHints(String q, int limit);

    List<String> findProductBrandsByCategory(String categorySlug);

    List<String> findProductBrandsByCategoryId(int categoryId);

    List<Product> getRelatedProducts(long productId, PageQuery pageQuery);

    PageData<Product> getProducts(ProductQuery query);
}
