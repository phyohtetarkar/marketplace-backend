package com.shoppingcenter.app.controller.review;

import com.shoppingcenter.app.controller.review.dto.ShopReviewDTO;
import com.shoppingcenter.app.controller.review.dto.ShopReviewEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.SortQuery.Direction;

public interface ShopReviewFacade {

    void writeReview(ShopReviewEditDTO review);

    void updateReview(ShopReviewEditDTO review);

    void delete(ShopReviewDTO review);

    ShopReviewDTO findUserReview(long shopId, long userId);

    PageData<ShopReviewDTO> findReviewsByShop(long shopId, Direction direction, Integer page);

}
