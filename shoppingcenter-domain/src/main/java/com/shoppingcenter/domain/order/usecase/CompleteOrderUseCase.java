package com.shoppingcenter.domain.order.usecase;

import java.time.YearMonth;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.shop.ShopMonthlySale;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;
import com.shoppingcenter.domain.shop.dao.ShopMonthlySaleDao;

import lombok.Setter;

@Setter
public class CompleteOrderUseCase {

	private OrderDao orderDao;
	
	private ShopMonthlySaleDao shopMonthlySaleDao;
	
	private ShopMemberDao shopMemberDao;
	
	
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
			sale.setShopId(shopId);
			sale.setYear(ym.getYear());
			sale.setMonth(ym.getMonthValue());
		}
		var currentTotalSale = sale.getTotalSale();
		sale.setTotalSale(currentTotalSale.add(order.getTotalPrice()));
		
		shopMonthlySaleDao.save(sale);
	}
}
