package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.product.dao.FavoriteProductDao;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.user.ProfileStatistic;

import lombok.Setter;

@Setter
public class GetProfileStatisticUseCase {

	private OrderDao orderDao;
	
	private FavoriteProductDao favoriteProductDao;
	
	private ShopMemberDao shopMemberDao;
	
	public ProfileStatistic apply(long userId) {
		var data = new ProfileStatistic();
		data.setTotalOrder(orderDao.getOrderCountByUser(userId));
		data.setTotalFavorite(favoriteProductDao.getFavoriteCountByUser(userId));
		data.setTotalShop(shopMemberDao.getCountByUser(userId));
		return data;
	}
	
}
