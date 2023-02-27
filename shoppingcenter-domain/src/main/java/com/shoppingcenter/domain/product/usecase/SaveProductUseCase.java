package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.Product;

public interface SaveProductUseCase {

    Product apply(Product product);

}
