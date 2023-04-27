package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.dao.OrderDao;

public class GetOrderByCodeUseCase {
	
	private OrderDao dao;
	
	public GetOrderByCodeUseCase(OrderDao dao) {
		super();
		this.dao = dao;
	}

	public Order apply(String code) {
		return dao.findByCode(code);
	}
	
}
