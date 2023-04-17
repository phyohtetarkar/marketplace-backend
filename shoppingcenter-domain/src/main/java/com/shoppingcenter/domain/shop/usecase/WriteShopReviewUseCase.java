package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class WriteShopReviewUseCase {

    private ShopReviewDao shopReviewDao;

    private ShopDao shopDao;

    private UserDao userDao;

    public void apply(ShopReview review) {
        // if (!shopDao.existsByIdAndStatus(review.getShopId(), Shop.Status.ACTIVE)) {
        // throw new ApplicationException("Shop not found");
        // }

        if (!shopDao.existsById(review.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        if (!userDao.existsById(review.getUserId())) {
            throw new ApplicationException("User not found");
        }

        // if (shopReviewDao.exists(review.getShopId(), review.getUserId())) {
        // throw new ApplicationException("Review already given");
        // }

        shopReviewDao.save(review);

        double averateRating = shopReviewDao.averageRatingByShop(review.getShopId());
        shopDao.updateRating(review.getShopId(), averateRating);
    }

}
