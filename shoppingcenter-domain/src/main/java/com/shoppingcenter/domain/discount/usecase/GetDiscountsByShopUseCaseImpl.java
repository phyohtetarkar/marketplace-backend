package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.domain.discount.DiscountDao;

public class GetDiscountsByShopUseCaseImpl implements GetDiscountsByShopUseCase {

    private DiscountDao dao;

    public GetDiscountsByShopUseCaseImpl(DiscountDao dao) {
        this.dao = dao;
    }

    @Override
    public PageData<Discount> apply(long shopId, Integer page) {
        return dao.findByShop(shopId, Utils.normalizePage(page));
    }

}
