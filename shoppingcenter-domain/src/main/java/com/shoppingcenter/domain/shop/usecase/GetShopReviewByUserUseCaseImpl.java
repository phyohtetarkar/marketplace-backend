package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;

public class GetShopReviewByUserUseCaseImpl implements GetShopReviewByUserUseCase {

    private ShopReviewDao dao;

    public GetShopReviewByUserUseCaseImpl(ShopReviewDao dao) {
        this.dao = dao;
    }

    @Override
    public ShopReview apply(long shopId, long userId) {
        return dao.findUserReview(shopId, userId);
    }

}
