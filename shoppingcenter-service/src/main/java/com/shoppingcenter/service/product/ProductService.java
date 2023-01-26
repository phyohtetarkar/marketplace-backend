package com.shoppingcenter.service.product;

import com.shoppingcenter.service.product.model.Product;

public interface ProductService {

	void save(Product product);

	void delete(long id);

}
