package com.shoppingcenter.core.shop;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.shop.model.ShopReview;

public interface ShopReviewService {

    void writeReview(ShopReview review);

    void deleteReview(String userId, long shopId);

    PageData<ShopReview> findReviewsByShop(long shopId);

}
