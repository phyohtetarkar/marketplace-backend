package com.shoppingcenter.domain.order.usecase;

import java.time.YearMonth;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.sale.SaleHistory;
import com.shoppingcenter.domain.sale.SaleHistoryDao;

import lombok.Setter;

@Setter
public class CompleteOrderUseCase {

	private OrderDao orderDao;
	
	private SaleHistoryDao saleHistoryDao;
	
	public void apply(long userId, long orderId) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getUser().getId() != userId) {
			throw new ApplicationException("Order not found");
		}
		
		if (order.getStatus() == Status.CANCELLED) {
			throw new ApplicationException("You cannot complete cancelled order");
		}
		
		orderDao.updateStatus(orderId, Status.COMPLETED);
		
		var shopId = order.getShop().getId();
		
		var ym = YearMonth.now();
		var history = saleHistoryDao.findByShopAndYearMonth(shopId, ym);
		if (history == null) {
			history = new SaleHistory();
			history.setShopId(shopId);
			history.setYear(ym.getYear());
			history.setMonth(ym.getMonthValue());
		}
		var currentTotalSale = history.getTotalSale();
		history.setTotalSale(currentTotalSale.add(order.getTotalPrice()));
		
		saleHistoryDao.save(history);
	}
}
