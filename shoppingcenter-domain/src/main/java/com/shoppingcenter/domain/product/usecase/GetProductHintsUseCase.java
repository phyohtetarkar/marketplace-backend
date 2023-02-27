package com.shoppingcenter.domain.product.usecase;

import java.util.List;

import com.shoppingcenter.domain.product.Product;

public interface GetProductHintsUseCase {

    List<Product> apply(String q);

}
