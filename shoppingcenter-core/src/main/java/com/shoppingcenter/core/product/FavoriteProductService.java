package com.shoppingcenter.core.product;

import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.product.model.Product;

public interface FavoriteProductService {

	void add(String userId, long productId);

	void remove(String userId, long productId);

	PageResult<Product> findByUser(String userId);
}
