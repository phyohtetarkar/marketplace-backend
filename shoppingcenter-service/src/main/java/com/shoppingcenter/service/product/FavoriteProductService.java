package com.shoppingcenter.service.product;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.product.model.Product;

public interface FavoriteProductService {

	void add(String userId, long productId);

	void remove(String userId, long productId);

	boolean checkFavorite(String userId, long productId);

	PageData<Product> findByUser(String userId, Integer page);
}
