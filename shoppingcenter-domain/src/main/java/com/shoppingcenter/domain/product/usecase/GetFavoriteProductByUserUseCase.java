package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;

public interface GetFavoriteProductByUserUseCase {

    PageData<Product> apply(String userId, Integer page);

}
