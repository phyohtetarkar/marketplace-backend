package com.shoppingcenter.service.shop;

import java.util.List;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.shop.model.Shop;

public interface ShopQueryService {

    Shop findById(long id);

    Shop findBySlug(String slug);

    boolean existsBySlug(String slug, Long excludeId);

    List<Shop> getHints(String q);

    PageData<Shop> findByUser(String userId, Integer page);

    PageData<Shop> findAll(ShopQuery query);

}
