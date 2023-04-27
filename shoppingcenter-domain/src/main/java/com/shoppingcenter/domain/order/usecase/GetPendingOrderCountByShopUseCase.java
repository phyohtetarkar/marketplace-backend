package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.order.dao.OrderDao;

public class GetPendingOrderCountByShopUseCase {

	private OrderDao dao;
	
	public GetPendingOrderCountByShopUseCase(OrderDao dao) {
		super();
		this.dao = dao;
	}

	public long apply(long shopId) {
		return dao.getPendingOrderCountByShop(shopId);
	}
	
}
