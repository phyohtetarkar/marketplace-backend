package com.shoppingcenter.core.shop;

import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.data.shop.ShopEntity.Status;

public interface ShopService {

	void save(Shop shop);

	void uploadLogo(long shopId, UploadFile file);

	void uploadCover(long shopId, UploadFile file);

	void updateStatus(long shopId, Status status);

	void delete(long id);

	Shop findById(long id);

	Shop findBySlug(String slug);

	PageResult<Shop> findByUser(String userId);

	PageResult<Shop> findAll(ShopQuery filter);

}
