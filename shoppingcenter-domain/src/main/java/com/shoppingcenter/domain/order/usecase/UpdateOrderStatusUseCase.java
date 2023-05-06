package com.shoppingcenter.domain.order.usecase;

import java.time.YearMonth;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.sale.SaleHistory;
import com.shoppingcenter.domain.sale.SaleHistoryDao;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCase;

import lombok.Setter;

@Setter
public class UpdateOrderStatusUseCase {
	
	private OrderDao orderDao;
	
	private SaleHistoryDao saleHistoryDao;
	
	private ValidateShopMemberUseCase validateShopMemberUseCase;

	public void apply(long userId, long orderId, Order.Status status) {
		
		var order = orderDao.findById(orderId);
		
		if (order == null) {
			throw new ApplicationException("Order not found");
		}
		
		var shopId = order.getShop().getId();
		
		validateShopMemberUseCase.apply(shopId, userId);
		
		if (order.getStatus() == Status.COMPLETED) {
			throw new ApplicationException("You cannot change status of completed order");
		}
		
		orderDao.updateStatus(order.getId(), status);
		
		if (status == Status.CANCELLED) {
		}
		
		if (status == Status.CONFIRMED) {
		}
		
		if (status == Status.COMPLETED) {
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
}
