package com.marketplace.data.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.product.ProductRepo;
import com.marketplace.data.product.ProductVariantRepo;
import com.marketplace.domain.order.OrderItem;
import com.marketplace.domain.order.dao.OrderItemDao;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
	
	@Autowired
	private OrderItemRepo orderItemRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductVariantRepo productVariantRepo;

	@Override
	public void createAll(long orderId, List<OrderItem> items) {
		var entities = items.stream().map(item -> {
			var entity = new OrderItemEntity();
			entity.setOrder(orderRepo.getReferenceById(orderId));
			entity.setProduct(productRepo.getReferenceById(item.getProduct().getId()));
			entity.setUnitPrice(item.getUnitPrice());
			entity.setDiscountPrice(item.getDiscountPrice());
			entity.setQuantity(item.getQuantity());
			if (item.getProductVariant() != null) {
				entity.setProductVariant(productVariantRepo.getReferenceById(item.getProductVariant().getId()));
			}
			return entity;
		}).toList();
		
		orderItemRepo.saveAll(entities);
	}
	
	@Override
	public void updateCancelled(long id, boolean cancelled) {
		orderItemRepo.updateCancelled(id, cancelled);
	}

	@Override
	public boolean exists(long id) {
		return orderItemRepo.existsById(id);
	}
	
	@Override
	public long countByOrderCancelledFalse(long orderId) {
		return orderItemRepo.countByOrderIdAndCancelledFalse(orderId);
	}

	@Override
	public OrderItem findById(long id) {
		return orderItemRepo.findById(id).map(OrderItemMapper::toDomain).orElse(null);
	}
	
	@Override
	public List<OrderItem> findByOrder(long orderId) {
		return orderItemRepo.findByOrderId(orderId).stream()
				.map(OrderItemMapper::toDomain)
				.toList();
	}

}
