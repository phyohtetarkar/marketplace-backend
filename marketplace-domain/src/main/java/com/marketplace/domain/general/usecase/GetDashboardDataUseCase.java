package com.marketplace.domain.general.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.general.DashboardData;
import com.marketplace.domain.product.dao.ProductDao;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.subscription.dao.ShopSubscriptionDao;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class GetDashboardDataUseCase {
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopSubscriptionDao shopSubscriptionDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	public DashboardData apply() {
		var data = new DashboardData();
		data.setTotalSubscription(shopSubscriptionDao.getTotalSubscriptionPrice());
		data.setTotalShop(shopDao.count());
		data.setTotalProduct(productDao.count());
		data.setTotalUser(userDao.count());
		data.setRecentSubscriptions(shopSubscriptionDao.findLatest10SuccessfulSubscription());
		return data;
	}
	
}
