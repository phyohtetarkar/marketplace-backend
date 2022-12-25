package com.shoppingcenter.core.shop;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.shop.model.Shop;

public interface ShopQueryService {

    Shop findById(long id);

    Shop findBySlug(String slug);

    PageData<Shop> findByUser(String userId, int page);

    PageData<Shop> findAll(ShopQuery query);

}
