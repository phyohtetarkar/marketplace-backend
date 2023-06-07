package com.shoppingcenter.domain.order.dao;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.order.DeliveryDetail;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.OrderQuery;
import com.shoppingcenter.domain.order.PaymentDetail;

public interface OrderDao {

	long create(Order order);
	
	void update(Order order);
	
	void updateStatus(long id, Order.Status status);
	
	void savePaymentDetail(PaymentDetail detail);
	
	void saveDeliveryDetail(DeliveryDetail detail);
	
	void updateReceiptImage(long orderId, String receiptImage);
	
	boolean existsById(long id);
	
	boolean existsByCode(String code);
	
	long getPendingOrderCountByShop(long shopId);
	
	long getOrderCountByShop(long shopId);
	
	long getOrderCountByUser(long userId);
	
	Order findById(long id);
	
	Order findByCode(String code);
	
	PageData<Order> find(OrderQuery query);
	
}
