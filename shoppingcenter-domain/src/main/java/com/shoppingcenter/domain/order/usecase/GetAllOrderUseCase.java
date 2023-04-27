package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.OrderQuery;
import com.shoppingcenter.domain.order.dao.OrderDao;

public class GetAllOrderUseCase {
	
	private OrderDao dao;
	
	public GetAllOrderUseCase(OrderDao dao) {
		super();
		this.dao = dao;
	}

	public PageData<Order> apply(OrderQuery query) {
		return dao.find(query);
	}
	
}
