package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class UpdateShopContactUseCase {

	private ShopDao shopDao;

	public UpdateShopContactUseCase(ShopDao shopDao) {
		super();
		this.shopDao = shopDao;
	}

	public void apply(ShopContact contact) {
		if (!shopDao.existsById(contact.getShopId())) {
			throw new ApplicationException("Shop not found");
		}

		if (contact.getCity() == null) {
			throw new ApplicationException("City not found");
		}

		shopDao.saveContact(contact);
	}

}
