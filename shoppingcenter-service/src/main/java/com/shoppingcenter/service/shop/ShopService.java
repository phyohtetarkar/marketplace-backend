package com.shoppingcenter.service.shop;

import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.shop.model.Shop;
import com.shoppingcenter.service.shop.model.ShopContact;
import com.shoppingcenter.service.shop.model.ShopGeneral;

public interface ShopService {

	void create(Shop shop);

	void updateGeneralInfo(ShopGeneral general);

	void updateContact(ShopContact contact);

	void uploadLogo(long shopId, UploadFile file);

	void uploadCover(long shopId, UploadFile file);

	void updateStatus(long shopId, Shop.Status status);

	void delete(long id);

}
