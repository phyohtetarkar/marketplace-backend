package com.shoppingcenter.domain.shop.dao;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.shop.ShopReview;

public interface ShopReviewDao {

    void save(ShopReview review);

    void delete(String userId, long id);

    boolean existsByUserAndShop(String userId, long shopId);

    double averageRatingByShop(long shopId);

    ShopReview findUserReview(long shopId, String userId);

    PageData<ShopReview> findReviewByShop(long shopId, PageQuery paging, SortQuery sort);

}
