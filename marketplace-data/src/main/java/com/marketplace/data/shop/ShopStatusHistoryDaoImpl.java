package com.marketplace.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.shop.ShopStatusHistory;
import com.marketplace.domain.shop.dao.ShopStatusHistoryDao;

@Repository
public class ShopStatusHistoryDaoImpl implements ShopStatusHistoryDao {
	
	@Autowired
	private ShopStatusHistoryRepo shopStatusHistoryRepo;
	
	@Autowired
	private ShopRepo shopRepo;

	@Override
	public void save(ShopStatusHistory history) {
		var entity = shopStatusHistoryRepo.findById(history.getId()).orElseGet(ShopStatusHistoryEntity::new);
		entity.setStatus(history.getStatus());
		entity.setRemark(history.getRemark());
		entity.setShop(shopRepo.getReferenceById(history.getShopId()));
		
		shopStatusHistoryRepo.save(entity);
	}

}
