package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.Shop;

public interface GetShopByIdUseCase {
    Shop apply(long id);
}
