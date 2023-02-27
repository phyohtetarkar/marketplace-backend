package com.shoppingcenter.app.controller.review;

import com.shoppingcenter.app.controller.review.dto.ShopReviewDTO;
import com.shoppingcenter.app.controller.review.dto.ShopReviewEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.SortQuery.Direction;

public interface ShopReviewFacade {

    void writeReview(ShopReviewEditDTO review);

    void updateReview(ShopReviewEditDTO review);

    void delete(String userId, long id);

    ShopReviewDTO findUserReview(long shopId, String userId);

    PageData<ShopReviewDTO> findReviewsByShop(long shopId, Direction direction, Integer page);

}
