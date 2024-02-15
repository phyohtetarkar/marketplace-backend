package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.general.CityDao;
import com.marketplace.domain.shop.ShopContactInput;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class UpdateShopContactUseCase {

	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Transactional
	public void apply(ShopContactInput values) {
		if (!shopDao.existsById(values.getShopId())) {
			throw new ApplicationException("Shop not found");
		}
		
		if (values.getPhones() == null || values.getPhones().isEmpty()) {
			throw new ApplicationException("Required at least one phone number");
		}
		
		if (!cityDao.existsById(values.getCityId())) {
			throw new ApplicationException("City not found");
		}

		shopDao.saveContact(values);
	}

}
