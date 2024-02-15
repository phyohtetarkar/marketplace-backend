package com.marketplace.domain.product.dao;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductUpdateInput;

public interface ProductDao {

    long create(ProductCreateInput values);
    
    long update(ProductUpdateInput values);

    void updateThumbnail(long id, String thumbnail);
    
    void removeDiscount(long discountId);

    void updateStatus(long id, Product.Status status);
    
    void updateDeleted(long productId, boolean deleted);
    
    void updateFeatured(long productId, boolean featured);
    
    void updatePrice(long productId, BigDecimal price);
    
    void updateDescription(long productId, String value);

    boolean existsById(long id);

    boolean existsBySlug(String slug);

    boolean existsByCategory(int categoryId);
    
    boolean existsByIdAndShop(long id, long shopId);
    
    boolean existsByIdNotAndSlug(long id, String slug);
    
    long count();

    long countByDiscount(long discountId);

    long countByShop(long shopId);
    
    Product findById(long id);

    Product findBySlug(String slug);

    List<Product> findProductHints(String q, int limit);

    List<String> findProductBrandsByQuery(String q);
    
    List<String> findProductBrandsByCategory(int lft, int rgt);
    
    BigDecimal getMaxPriceByNameLike(String q);
    
    BigDecimal getMaxPriceByCategory(int lft, int rgt);

    List<Product> getRelatedProducts(long productId, PageQuery pageQuery);
    
    List<Product> getTopFeaturedProducts();
    
    List<Product> getTopDiscountProducts();

    PageData<Product> findAll(SearchQuery searchQuery);
}
