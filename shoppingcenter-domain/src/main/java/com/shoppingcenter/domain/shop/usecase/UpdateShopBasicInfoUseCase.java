package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopGeneral;

public interface UpdateShopBasicInfoUseCase {

    Shop apply(ShopGeneral general);

}
