package com.shoppingcenter.domain.shop.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopCreateInput;
import com.shoppingcenter.domain.shop.ShopGeneral;
import com.shoppingcenter.domain.shop.ShopQuery;

public interface ShopDao {

    long create(ShopCreateInput data);

    void updateGeneralInfo(ShopGeneral general);

    void saveContact(ShopContact contact);

    void updateLogo(long shopId, String logo);

    void updateCover(long shopId, String cover);

    void updateRating(long shopId, double rating);
    
    void updateDisabled(long shopId, boolean disabled);
    
    void updateActivated(long shopId, boolean activated);

    void delete(long id);

    boolean existsById(long id);

    boolean existsBySlug(String slug);

    Shop.Status getStatus(long shopId);

    String getLogo(long shopId);

    String getCover(long shopId);

    Shop findById(long id);

    Shop findBySlug(String slug);

    List<Shop> getShopHints(String q, int limit);

    PageData<Shop> findByUser(long userId, int page);

    PageData<Shop> getShops(ShopQuery query);
}
