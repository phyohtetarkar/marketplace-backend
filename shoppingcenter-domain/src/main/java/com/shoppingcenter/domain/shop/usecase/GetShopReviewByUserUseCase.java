package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;

public class GetShopReviewByUserUseCase {

    private ShopReviewDao dao;

    public GetShopReviewByUserUseCase(ShopReviewDao dao) {
        this.dao = dao;
    }

    public ShopReview apply(long shopId, long userId) {
        return dao.findUserReview(shopId, userId);
    }

}
