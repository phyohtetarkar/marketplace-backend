package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;

import lombok.Setter;

@Setter
public class WriteShopReviewUseCaseImpl implements WriteShopReviewUseCase {

    private ShopReviewDao shopReviewDao;

    private ShopDao shopDao;

    private ValidateShopActiveUseCase validateShopActiveUseCase;

    @Override
    public void apply(ShopReview review) {
        // if (!shopDao.existsByIdAndStatus(review.getShopId(), Shop.Status.ACTIVE)) {
        // throw new ApplicationException("Shop not found");
        // }

        validateShopActiveUseCase.apply(review.getShopId());

        if (review.getId() <= 0 && shopReviewDao.existsByUserAndShop(review.getUserId(), review.getShopId())) {
            throw new ApplicationException("Review already given");
        }

        shopReviewDao.save(review);

        double averateRating = shopReviewDao.averageRatingByShop(review.getShopId());
        shopDao.updateRating(review.getShopId(), averateRating);
    }

}
