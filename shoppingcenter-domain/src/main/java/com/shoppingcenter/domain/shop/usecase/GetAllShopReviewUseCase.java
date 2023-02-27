package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.shop.ShopReview;

public interface GetAllShopReviewUseCase {

    PageData<ShopReview> apply(long shopId, Integer page, SortQuery sort);

}
