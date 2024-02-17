package com.marketplace.data.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.JpaSpecificationBuilder;
import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.shop.ShopRepo;
import com.marketplace.data.user.UserRepo;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.order.DeliveryDetail;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.Order.Status;
import com.marketplace.domain.order.PaymentDetail;
import com.marketplace.domain.order.dao.OrderDao;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private DeliveryDetailRepo deliveryDetailRepo;
	
	@Autowired
	private PaymentDetailRepo paymentDetailRepo;
	
	@Autowired
	private ShopRepo shopRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public long create(long shopId, long customerId, Order order) {
		var entity = new OrderEntity();
		entity.setOrderCode(order.getOrderCode());
		entity.setSubTotalPrice(order.getSubTotalPrice());
		entity.setTotalPrice(order.getTotalPrice());
		entity.setQuantity(order.getQuantity());
		entity.setDiscountPrice(order.getDiscountPrice());
		entity.setStatus(order.getStatus());
		entity.setPaymentMethod(order.getPaymentMethod());
		entity.setNote(order.getNote());
		
		entity.setCustomer(userRepo.getReferenceById(customerId));
		entity.setShop(shopRepo.getReferenceById(shopId));
		
		var result = orderRepo.save(entity);
		
		return result.getId();
	}
	
	@Override
	public void update(Order order) {
		var entity = orderRepo.findById(order.getId()).orElseThrow(() -> new ApplicationException("Order not found"));
		entity.setSubTotalPrice(order.getSubTotalPrice());
		entity.setTotalPrice(order.getTotalPrice());
		entity.setQuantity(order.getQuantity());
		entity.setDiscountPrice(order.getDiscountPrice());
		entity.setStatus(order.getStatus());
		entity.setPaymentMethod(order.getPaymentMethod());
		entity.setNote(order.getNote());
		
		orderRepo.save(entity);
	}

	@Override
	public void updateStatus(long id, Status status) {
		orderRepo.updateStatus(id, status);
	}
	
	@Override
	public void saveDeliveryDetail(long orderId, DeliveryDetail detail) {
		var entity = deliveryDetailRepo.findById(orderId).orElseGet(DeliveryDetailEntity::new);
		entity.setOrder(orderRepo.getReferenceById(orderId));
		entity.setName(detail.getName());
		entity.setPhone(detail.getPhone());
		entity.setAddress(detail.getAddress());
		
		deliveryDetailRepo.save(entity);
	}
	
	@Override
	public void savePaymentDetail(long orderId, PaymentDetail detail) {
		var entity = paymentDetailRepo.findById(orderId).orElseGet(PaymentDetailEntity::new);
		entity.setAccountType(detail.getAccountType());
		entity.setReceiptImage(detail.getReceiptImage());
		entity.setOrder(orderRepo.getReferenceById(orderId));
		
		paymentDetailRepo.save(entity);
		
	}
	
	@Override
	public void updateReceiptImage(long orderId, String receiptImage) {
		paymentDetailRepo.updateReceiptImage(orderId, receiptImage);
	}

	@Override
	public boolean existsById(long id) {
		return orderRepo.existsById(id);
	}

	@Override
	public boolean existsByCode(String code) {
		return orderRepo.existsByOrderCode(code);
	}
	
	@Override
	public long getOrderCountByUser(long userId) {
		return orderRepo.countByCustomerId(userId);
	}

	@Override
	public long getPendingOrderCountByShop(long shopId) {
		return orderRepo.countByShopIdAndStatus(shopId, Order.Status.PENDING);
	}
	
	@Override
	public long getOrderCountByShop(long shopId) {
		return orderRepo.countByShopId(shopId);
	}

	@Override
	public Order findById(long id) {
		return orderRepo.findById(id).map(OrderMapper::toDomain).orElse(null);
	}

	@Override
	public Order findByCode(String code) {
		return orderRepo.findByOrderCode(code).map(OrderMapper::toDomain).orElse(null);
	}

	@Override
	public PageData<Order> findAll(SearchQuery searchQuery) {
		var spec = JpaSpecificationBuilder.build(searchQuery.getCriterias(), OrderEntity.class);

        var pageable = PageQueryMapper.fromPageQuery(searchQuery.getPageQuery());

        var pageResult = spec != null ? orderRepo.findAll(spec, pageable) : orderRepo.findAll(pageable);
		
        return PageDataMapper.map(pageResult, OrderMapper::toDomainCompat);
	}


}
