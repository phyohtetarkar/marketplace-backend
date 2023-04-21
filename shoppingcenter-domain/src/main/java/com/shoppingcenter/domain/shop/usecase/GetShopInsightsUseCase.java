package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.ShopStatistic;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class GetShopInsightsUseCase {

    private ShopDao shopDao;

    private ProductDao productDao;

    public ShopStatistic apply(long shopId) {
        if (!shopDao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }

        ShopStatistic insights = new ShopStatistic();
        insights.setTotalProduct(productDao.countByShop(shopId));
        return insights;
    }

}
