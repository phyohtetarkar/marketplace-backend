package com.shoppingcenter.domain.shop.usecase;

import java.util.List;

import com.shoppingcenter.domain.shop.Shop;

public interface GetShopHintsUseCase {

    List<Shop> apply(String q);

}
