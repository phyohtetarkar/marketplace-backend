package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.discount.Discount;

public interface GetDiscountsByShopUseCase {

    PageData<Discount> apply(long shopId, Integer page);

}
