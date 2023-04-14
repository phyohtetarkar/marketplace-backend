package com.shoppingcenter.domain.shop.dao;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.shop.ShopReview;

public interface ShopReviewDao {

    void save(ShopReview review);

    void delete(long shopId, long userId);

    boolean exists(long shopId, long userId);

    double averageRatingByShop(long shopId);

    ShopReview findUserReview(long shopId, long userId);

    PageData<ShopReview> findReviewByShop(long shopId, PageQuery paging, SortQuery sort);

}
