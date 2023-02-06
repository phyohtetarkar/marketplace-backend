package com.shoppingcenter.service.shop;

import org.springframework.data.domain.Sort.Direction;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.shop.model.ShopReview;

public interface ShopReviewService {

    void writeReview(ShopReview review);

    void updateReview(ShopReview review);

    void delete(String userId, long id);

    ShopReview findUserReview(long shopId, String userId);

    PageData<ShopReview> findReviewsByShop(long shopId, Direction direction, Integer page);

}
