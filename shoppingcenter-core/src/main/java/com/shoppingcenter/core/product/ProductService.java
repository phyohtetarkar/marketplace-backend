package com.shoppingcenter.core.product;

import com.shoppingcenter.core.product.model.Product;

public interface ProductService {

	void save(Product product);

	void delete(long id);

}
