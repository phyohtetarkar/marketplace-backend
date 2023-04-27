package com.shoppingcenter.data.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.order.OrderItem;
import com.shoppingcenter.domain.order.dao.OrderItemDao;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
	
	@Autowired
	private OrderItemRepo orderItemRepo;
	
	@Autowired
	private OrderRepo orderRepo;

	@Override
	public void createAll(List<OrderItem> items) {
		var entities = items.stream().map(item -> {
			var entity = new OrderItemEntity();
			entity.setOrder(orderRepo.getReferenceById(item.getOrderId()));
			entity.setProductId(item.getProductId());
			entity.setProductName(item.getProductName());
			entity.setProductSlug(item.getProductSlug());
			entity.setProductImage(item.getProductImage());
			entity.setUnitPrice(item.getUnitPrice());
			entity.setDiscount(item.getUnitPrice());
			entity.setQuantity(item.getQuantity());
			entity.setVariant(item.getVariant());
			return entity;
		}).toList();
		
		orderItemRepo.saveAll(entities);
	}
	
	@Override
	public void updateRemoved(long id, boolean removed) {
		orderItemRepo.updateRemoved(id, removed);
	}

	@Override
	public boolean exists(long id) {
		return orderItemRepo.existsById(id);
	}

	@Override
	public OrderItem findById(long id) {
		return orderItemRepo.findById(id).map(OrderItemMapper::toDomain).orElse(null);
	}

}
