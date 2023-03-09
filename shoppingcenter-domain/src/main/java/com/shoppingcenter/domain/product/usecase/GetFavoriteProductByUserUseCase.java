package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.FavoriteProduct;

public interface GetFavoriteProductByUserUseCase {

    PageData<FavoriteProduct> apply(String userId, Integer page);

}
