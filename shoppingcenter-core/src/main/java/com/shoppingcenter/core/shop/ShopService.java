package com.shoppingcenter.core.shop;

import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.core.shop.model.ShopContact;
import com.shoppingcenter.core.shop.model.ShopGeneral;
import com.shoppingcenter.data.shop.ShopEntity.Status;

public interface ShopService {

	void create(Shop shop);

	void updateGeneralInfo(ShopGeneral general);

	void updateContact(ShopContact contact);

	void uploadLogo(long shopId, UploadFile file);

	void uploadCover(long shopId, UploadFile file);

	void updateStatus(long shopId, Status status);

	void delete(long id);

}
