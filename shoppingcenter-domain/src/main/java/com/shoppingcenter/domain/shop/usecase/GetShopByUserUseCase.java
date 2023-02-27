package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;

public interface GetShopByUserUseCase {

    PageData<Shop> apply(String userId, Integer page);

}
