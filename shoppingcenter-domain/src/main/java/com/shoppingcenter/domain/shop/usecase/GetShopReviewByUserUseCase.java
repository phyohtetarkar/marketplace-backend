package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopReview;

public interface GetShopReviewByUserUseCase {

    ShopReview apply(long shopId, String userId);
}
