package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;

public interface GetAllShopUseCase {

    PageData<Shop> apply(ShopQuery query);

}
