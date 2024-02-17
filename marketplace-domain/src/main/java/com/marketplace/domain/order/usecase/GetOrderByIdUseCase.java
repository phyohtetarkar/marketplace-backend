package com.marketplace.domain.order.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.dao.OrderDao;

@Component
public class GetOrderByIdUseCase {
	
	@Autowired
	private OrderDao dao;

	@Transactional(readOnly = true)
	public Order apply(long id) {
		return dao.findById(id);
	}
	
}
