package com.marketplace.domain.order.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.dao.OrderDao;

@Component
public class GetOrderByCodeUseCase {
	
	@Autowired
	private OrderDao dao;

	@Transactional(readOnly = true)
	public Order apply(String code) {
		return dao.findByCode(code);
	}
	
}
