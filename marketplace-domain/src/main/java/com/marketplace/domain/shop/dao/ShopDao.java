package com.marketplace.domain.shop.dao;

import java.util.List;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopContactInput;
import com.marketplace.domain.shop.ShopCreateInput;
import com.marketplace.domain.shop.ShopUpdateInput;

public interface ShopDao {

    long create(ShopCreateInput data);

    Shop update(ShopUpdateInput general);
    
    void saveContact(ShopContactInput values);

    void updateLogo(long shopId, String logo);

    void updateCover(long shopId, String cover);
    
    void updateStatus(long shopId, Shop.Status status);
    
    void updateExpiredAt(long shopId, long value);
    
    void updateFeatured(long shopId, boolean value);
    
    boolean existsById(long id);

    boolean existsBySlug(String slug);
    
    boolean existsByIdNotAndSlug(long id, String slug);
    
    boolean existsByIdAndExpiredAtGreaterThanAndStatusActive(long shopId, long currentTime);
    
    long count();
    
    Shop.Status getStatus(long shopId);

    String getLogo(long shopId);

    String getCover(long shopId);
    
    long getExpiredAt(long shopId);

    Shop findById(long id);

    Shop findBySlug(String slug);

    List<Shop> getShopHints(String q, int limit);

    PageData<Shop> findByUser(long userId, PageQuery pageQuery);

    PageData<Shop> findAll(SearchQuery searchQuery);
}
