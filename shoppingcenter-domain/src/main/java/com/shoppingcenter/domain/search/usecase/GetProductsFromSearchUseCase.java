package com.shoppingcenter.domain.search.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface GetProductsFromSearchUseCase {

    PageData<Product> apply(ProductQuery query);

}
