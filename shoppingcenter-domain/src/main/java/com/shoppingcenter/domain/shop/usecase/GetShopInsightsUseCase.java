package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopInsights;

public interface GetShopInsightsUseCase {

    ShopInsights apply(long shopId);

}
