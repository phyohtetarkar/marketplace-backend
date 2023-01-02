package com.shoppingcenter.core.shop;

import java.util.List;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.shop.model.Shop;

public interface ShopQueryService {

    Shop findById(long id);

    Shop findBySlug(String slug);

    List<Shop> getHints(String q);

    PageData<Shop> findByUser(String userId, Integer page);

    PageData<Shop> findAll(ShopQuery query);

}
