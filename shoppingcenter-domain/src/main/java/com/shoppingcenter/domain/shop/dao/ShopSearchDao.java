package com.shoppingcenter.domain.shop.dao;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;

public interface ShopSearchDao {

    String save(Shop shop);

    void delete(long shopId);

    PageData<Shop> getShops(ShopQuery query);

}
