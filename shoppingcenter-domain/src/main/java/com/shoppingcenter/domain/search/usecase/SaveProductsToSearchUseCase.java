package com.shoppingcenter.domain.search.usecase;

import com.shoppingcenter.domain.product.Product;

public interface SaveProductsToSearchUseCase {

    void apply(Iterable<Product> products);

}
