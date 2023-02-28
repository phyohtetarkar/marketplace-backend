package com.shoppingcenter.domain.search.usecase;

import com.shoppingcenter.domain.product.Product;

public interface SaveProductToSearchUseCase {

    void apply(Product product);

}
