package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;

public class WriteShopReviewUseCaseImpl implements WriteShopReviewUseCase {

    private ShopReviewDao shopReviewDao;

    private ShopDao shopDao;

    public WriteShopReviewUseCaseImpl(ShopReviewDao shopReviewDao, ShopDao shopDao) {
        this.shopReviewDao = shopReviewDao;
        this.shopDao = shopDao;
    }

    @Override
    public void apply(ShopReview review) {
        if (!shopDao.existsByIdAndStatus(review.getShopId(), Shop.Status.ACTIVE)) {
            throw new ApplicationException("Shop not found");
        }

        if (review.getId() <= 0 && shopReviewDao.existsByUserAndShop(review.getUserId(), review.getShopId())) {
            throw new ApplicationException("Review already given");
        }

        shopReviewDao.save(review);

        double averateRating = shopReviewDao.averageRatingByShop(review.getShopId());
        shopDao.updateRating(review.getShopId(), averateRating);
    }

}
