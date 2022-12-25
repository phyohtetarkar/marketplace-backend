package com.shoppingcenter.core.product;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.product.model.Product;

public interface FavoriteProductService {

	void add(String userId, long productId);

	void remove(String userId, long productId);

	PageData<Product> findByUser(String userId, int page);
}
