package com.marketplace.domain.order.usecase;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.order.OrderStatusHistory;
import com.marketplace.domain.order.Order.Status;
import com.marketplace.domain.order.dao.OrderDao;
import com.marketplace.domain.order.dao.OrderStatusHistoryDao;
import com.marketplace.domain.shop.ShopMonthlySale;
import com.marketplace.domain.shop.dao.ShopMemberDao;
import com.marketplace.domain.shop.dao.ShopMonthlySaleDao;

@Component
public class CompleteOrderUseCase {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ShopMonthlySaleDao shopMonthlySaleDao;
	
	@Autowired
	private ShopMemberDao shopMemberDao;
	
	@Autowired
	private OrderStatusHistoryDao orderStatusHistoryDao;
	
	@Retryable(noRetryFor = { ApplicationException.class })
	@Transactional
	public void apply(long userId, long orderId) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		var shopId = order.getShop().getId();
		
		var seller = shopMemberDao.existsByShopAndUser(order.getShop().getId(), userId);
		
		if (!seller) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getStatus() == Status.CANCELLED) {
			throw new ApplicationException("You cannot complete cancelled order");
		}
		
		orderDao.updateStatus(orderId, Status.COMPLETED);
		
		var ym = YearMonth.now();
		var sale = shopMonthlySaleDao.findByShopAndYearMonth(shopId, ym);
		if (sale == null) {
			sale = new ShopMonthlySale();
			sale.setYear(ym.getYear());
			sale.setMonth(ym.getMonthValue());
			sale.setTotalSale(order.getTotalPrice());
			shopMonthlySaleDao.save(shopId, sale);
		} else {
			shopMonthlySaleDao.updateTotalSale(shopId, ym, order.getTotalPrice());
		}
		
		var history = new OrderStatusHistory();
		history.setOrderId(orderId);
		history.setStatus(Status.COMPLETED);
		history.setRemark("Order completed");
		
		orderStatusHistoryDao.save(history);
	}
}
