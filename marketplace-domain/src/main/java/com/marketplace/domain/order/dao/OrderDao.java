package com.marketplace.domain.order.dao;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.order.DeliveryDetail;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.PaymentDetail;

public interface OrderDao {

	long create(long shopId, long customerId, Order order);
	
	void update(Order order);
	
	void updateStatus(long id, Order.Status status);
	
	void savePaymentDetail(long orderId, PaymentDetail detail);
	
	void saveDeliveryDetail(long orderId, DeliveryDetail detail);
	
	void updateReceiptImage(long orderId, String receiptImage);
	
	boolean existsById(long id);
	
	boolean existsByCode(String code);
	
	long getPendingOrderCountByShop(long shopId);
	
	long getOrderCountByShop(long shopId);
	
	long getOrderCountByUser(long userId);
	
	Order findById(long id);
	
	Order findByCode(String code);
	
	PageData<Order> findAll(SearchQuery searchQuery);
	
}
