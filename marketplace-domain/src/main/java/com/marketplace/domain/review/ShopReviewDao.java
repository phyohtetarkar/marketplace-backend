package com.marketplace.domain.review;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;

public interface ShopReviewDao {

    void save(ShopReviewInput values);

    void delete(long shopId, long userId);

    boolean exists(long shopId, long userId);

    ShopReview findUserReview(long shopId, long userId);

    PageData<ShopReview> findReviewByShop(long shopId, PageQuery pageQuery);

}
