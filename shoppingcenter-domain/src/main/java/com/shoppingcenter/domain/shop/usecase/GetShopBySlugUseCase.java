package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.Shop;

public interface GetShopBySlugUseCase {

    Shop apply(String slug);

}
