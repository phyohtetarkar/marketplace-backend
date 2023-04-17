package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.ShopInsights;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class GetShopInsightsUseCase {

    private ShopDao shopDao;

    private ProductDao productDao;

    public ShopInsights apply(long shopId) {
        if (!shopDao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }

        ShopInsights insights = new ShopInsights();
        insights.setTotalProduct(productDao.countByShop(shopId));
        return insights;
    }

}
