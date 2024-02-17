package com.marketplace.domain.review.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.review.ShopReview;
import com.marketplace.domain.review.ShopReviewDao;

@Component
public class GetShopReviewByUserUseCase {

	@Autowired
    private ShopReviewDao dao;

    public ShopReview apply(long shopId, long userId) {
        return dao.findUserReview(shopId, userId);
    }

}
