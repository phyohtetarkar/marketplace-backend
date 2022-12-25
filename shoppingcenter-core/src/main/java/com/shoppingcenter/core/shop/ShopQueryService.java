package com.shoppingcenter.core.shop;

import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.shop.model.Shop;

public interface ShopQueryService {

    Shop findById(long id);

    Shop findBySlug(String slug);

    PageResult<Shop> findByUser(String userId, int page);

    PageResult<Shop> findAll(ShopQuery query);

}
