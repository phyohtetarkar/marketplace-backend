package com.shoppingcenter.domain.product.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductAttribute;
import com.shoppingcenter.domain.product.ProductEditInput;
import com.shoppingcenter.domain.product.ProductQuery;

public interface ProductDao {

    long save(ProductEditInput data);

    void updateThumbnail(long id, String thumbnail);
    
    void updateStockLeft(long id, int stockLeft);
    
    void delete(long id);

    void removeDiscount(long discountId);

    void toggleDisabled(long id, boolean disabled);

    void updateStatus(long id, Product.Status status);

    boolean existsById(long id);

    boolean existsBySlug(String slug);

    boolean existsByCategoryId(int categoryId);

    long countByDiscount(long discountId);

    long countByShop(long shopId);
    
    Product findById(long id);

    Product findBySlug(String slug);
    
    List<ProductAttribute> getProductAttributes(long productId);

    List<Product> findProductHints(String q, int limit);

    List<String> findProductBrandsByQuery(String q);
    
    List<String> findProductBrandsByCategoryId(int categoryId);

    List<Product> getRelatedProducts(long productId, PageQuery pageQuery);

    PageData<Product> getProducts(ProductQuery query);
}
