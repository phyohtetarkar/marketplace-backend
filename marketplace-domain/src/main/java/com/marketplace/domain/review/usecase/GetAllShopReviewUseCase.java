package com.marketplace.domain.review.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.review.ShopReview;
import com.marketplace.domain.review.ShopReviewDao;

@Component
public class GetAllShopReviewUseCase {

	@Autowired
    private ShopReviewDao dao;

	@Transactional(readOnly = true)
    public PageData<ShopReview> apply(long shopId, Integer page) {
        var pageQuery = PageQuery.of(page, Constants.PAGE_SIZE);
        return dao.findReviewByShop(shopId, pageQuery);
    }

}
