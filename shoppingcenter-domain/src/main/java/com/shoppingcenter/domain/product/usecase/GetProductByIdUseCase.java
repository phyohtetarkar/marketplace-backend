package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.Product;

public interface GetProductByIdUseCase {
    Product apply(long id);
}
