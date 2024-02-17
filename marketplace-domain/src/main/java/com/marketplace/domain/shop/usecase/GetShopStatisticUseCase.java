package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.order.dao.OrderDao;
import com.marketplace.domain.product.dao.ProductDao;
import com.marketplace.domain.shop.ShopStatistic;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopMonthlySaleDao;

@Component
public class GetShopStatisticUseCase {

	@Autowired
    private ShopDao shopDao;
    
	@Autowired
    private ProductDao productDao;
    
	@Autowired
    private OrderDao orderDao;
    
	@Autowired
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
