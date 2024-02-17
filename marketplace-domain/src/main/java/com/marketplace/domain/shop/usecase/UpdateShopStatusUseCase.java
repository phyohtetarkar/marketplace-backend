package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopStatusHistory;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopStatusHistoryDao;

@Component
public class UpdateShopStatusUseCase {

	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopStatusHistoryDao shopStatusHistoryDao;
	
	@Transactional
	public void apply(long shopId, Shop.Status status) {
		if (!shopDao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}
		shopDao.updateStatus(shopId, status);
		
		var history = new ShopStatusHistory();
		history.setShopId(shopId);
		history.setStatus(status);
		
		if (status == Shop.Status.APPROVED) {
			history.setRemark("Shop approved");
		} else if (status == Shop.Status.DISABLED) {
			history.setRemark("Shop disabled");
		}
		
		shopStatusHistoryDao.save(history);
	}
	
}
