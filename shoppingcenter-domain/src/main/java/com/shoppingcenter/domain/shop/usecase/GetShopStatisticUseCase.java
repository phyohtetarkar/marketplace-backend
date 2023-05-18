package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.shop.ShopStatistic;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopMonthlySaleDao;

import lombok.Setter;

@Setter
public class GetShopStatisticUseCase {

    private ShopDao shopDao;
    
    private ProductDao productDao;
    
    private OrderDao orderDao;
    
    private ShopMonthlySaleDao monthlySaleDao;

    public ShopStatistic apply(long shopId) {
        if (!shopDao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }
        
        var statistic = new ShopStatistic();
        
        statistic.setTotalProduct(productDao.countByShop(shopId));
        statistic.setPendingOrder(orderDao.getPendingOrderCountByShop(shopId));
        statistic.setTotalOrder(orderDao.getOrderCountByShop(shopId));
        statistic.setTotalSale(monthlySaleDao.getTotalSaleByShop(shopId));
        
        return statistic;
    }

}
