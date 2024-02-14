package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.order.dao.OrderDao;
import com.marketplace.domain.product.dao.FavoriteProductDao;
import com.marketplace.domain.shop.dao.ShopMemberDao;
import com.marketplace.domain.user.ProfileStatistic;

@Component
public class GetProfileStatisticUseCase {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private FavoriteProductDao favoriteProductDao;
	
	@Autowired
	private ShopMemberDao shopMemberDao;
	
	public ProfileStatistic apply(long userId) {
		var data = new ProfileStatistic();
		data.setTotalOrder(orderDao.getOrderCountByUser(userId));
		data.setTotalFavorite(favoriteProductDao.getFavoriteCountByUser(userId));
		data.setTotalShop(shopMemberDao.getCountByUser(userId));
		return data;
	}
	
}
