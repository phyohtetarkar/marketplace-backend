package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;

public class GetAllShopReviewUseCase {

    private ShopReviewDao dao;

    public GetAllShopReviewUseCase(ShopReviewDao dao) {
        this.dao = dao;
    }

    public PageData<ShopReview> apply(long shopId, Integer page, SortQuery sort) {
        var paging = PageQuery.of(Utils.normalizePage(page), Constants.PAGE_SIZE);
        return dao.findReviewByShop(shopId, paging, sort);
    }

}
