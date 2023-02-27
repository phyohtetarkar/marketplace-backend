package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface GetAllProductUseCase {

    PageData<Product> apply(ProductQuery query);

}
