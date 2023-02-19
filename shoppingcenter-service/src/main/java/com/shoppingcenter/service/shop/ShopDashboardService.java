package com.shoppingcenter.service.shop;

import com.shoppingcenter.service.shop.model.ShopInsights;

public interface ShopDashboardService {

    ShopInsights getInsights(long shopId);

}
