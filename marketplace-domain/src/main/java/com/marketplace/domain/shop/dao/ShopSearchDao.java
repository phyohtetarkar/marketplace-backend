package com.marketplace.domain.shop.dao;

import java.util.List;

import com.marketplace.domain.shop.Shop;

public interface ShopSearchDao {

    long save(Shop shop);

    void delete(long shopId);

    List<String> getSuggestions(String q, int limit);

}
