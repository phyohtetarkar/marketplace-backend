package com.marketplace.data.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.order.OrderStatusHistory;
import com.marketplace.domain.order.dao.OrderStatusHistoryDao;

@Repository
public class OrderStatusHistoryDaoImpl implements OrderStatusHistoryDao {

	@Autowired
	private OrderStatusHistoryRepo orderStatusHistoryRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Override
	public void save(OrderStatusHistory history) {
		var entity = orderStatusHistoryRepo.findById(history.getId())
				.orElseGet(OrderStatusHistoryEntity::new);
		entity.setStatus(history.getStatus());
		entity.setRemark(history.getRemark());
		entity.setOrder(orderRepo.getReferenceById(history.getOrderId()));
		
		orderStatusHistoryRepo.save(entity);
	}

}
