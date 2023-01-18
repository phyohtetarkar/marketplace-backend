package com.shoppingcenter.core.shop;

import org.springframework.data.domain.Sort.Direction;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.shop.model.ShopReview;

public interface ShopReviewService {

    void writeReview(ShopReview review);

    void delete(String id, String userId);

    PageData<ShopReview> findReviewsByShop(long shopId, Direction direction, Integer page);

}
