package com.shoppingcenter.data.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.product.ProductVariantAttributeEntity;
import com.shoppingcenter.domain.order.OrderItem;
import com.shoppingcenter.domain.order.dao.OrderItemDao;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
	
	@Autowired
	private OrderItemRepo orderItemRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	public void createAll(List<OrderItem> items) {
		var entities = items.stream().map(item -> {
			var entity = new OrderItemEntity();
			entity.setOrder(orderRepo.getReferenceById(item.getOrderId()));
			entity.setProduct(productRepo.getReferenceById(item.getProductId()));
			entity.setProductName(item.getProductName());
			entity.setProductSlug(item.getProductSlug());
			entity.setUnitPrice(item.getUnitPrice());
			entity.setDiscount(item.getDiscount());
			entity.setQuantity(item.getQuantity());
			if (item.getAttributes() != null && item.getAttributes().size() > 0) {
				entity.setAttributes(item.getAttributes().stream().map(a -> {
					var en = new ProductVariantAttributeEntity();
					en.setAttributeId(a.getAttributeId());
					en.setAttribute(a.getAttribute());
					en.setValue(a.getValue());
					en.setSort(a.getSort());
					return en;
				}).collect(Collectors.toSet()));
			}
			return entity;
		}).toList();
		
		orderItemRepo.saveAll(entities);
	}
	
	@Override
	public void removeProductRelation(long productId) {
		orderItemRepo.removeProductRelation(productId);
	}

	@Override
	public boolean exists(long id) {
		return orderItemRepo.existsById(id);
	}

	@Override
	public OrderItem findById(long id) {
		return orderItemRepo.findById(id).map(OrderItemMapper::toDomain).orElse(null);
	}
	
	@Override
	public List<OrderItem> findByOrder(long orderId) {
		return orderItemRepo.findByOrderId(orderId).stream().map(OrderItemMapper::toDomain).toList();
	}

}
