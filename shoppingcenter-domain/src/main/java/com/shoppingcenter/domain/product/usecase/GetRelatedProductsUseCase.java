package com.shoppingcenter.domain.product.usecase;

import java.util.List;

import com.shoppingcenter.domain.product.Product;

public interface GetRelatedProductsUseCase {

    List<Product> apply(long productId, int size);

}
