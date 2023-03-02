package com.shoppingcenter.domain.shop.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class GetShopHintsUseCaseImpl implements GetShopHintsUseCase {

    private ShopDao dao;

    public GetShopHintsUseCaseImpl(ShopDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Shop> apply(String q) {
        if (!Utils.hasText(q)) {
            return new ArrayList<>();
        }

        return dao.getShopByNameOrHeadlineLimit(q.toLowerCase(), 8);
    }

}
