package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.Product;

public interface GetProductBySlugUseCase {

    Product apply(String slug);
}
