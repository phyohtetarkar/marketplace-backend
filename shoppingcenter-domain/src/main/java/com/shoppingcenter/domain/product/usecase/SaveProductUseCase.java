package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.Product;

public interface SaveProductUseCase {

    void apply(Product product);

}
