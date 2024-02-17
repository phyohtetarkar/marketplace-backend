package com.marketplace.domain.order.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.order.dao.OrderDao;

@Component
public class GetPendingOrderCountByShopUseCase {

	@Autowired
	private OrderDao dao;

	public long apply(long shopId) {
		return dao.getPendingOrderCountByShop(shopId);
	}
	
}
