package com.shoppingcenter.domain.shop.dao;

import java.util.List;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;

public interface ShopSearchDao {

    long save(Shop shop);

    void delete(long shopId);

    List<String> getSuggestions(String q, int limit);

    List<Shop> getHints(String q, int limit);

    PageData<Shop> getShops(ShopQuery query);

}
