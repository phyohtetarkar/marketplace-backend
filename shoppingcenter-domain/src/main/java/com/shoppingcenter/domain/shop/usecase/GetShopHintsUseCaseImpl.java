package com.shoppingcenter.domain.shop.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;

public class GetShopHintsUseCaseImpl implements GetShopHintsUseCase {

    private ShopSearchDao dao;

    public GetShopHintsUseCaseImpl(ShopSearchDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Shop> apply(String q) {
        if (!Utils.hasText(q)) {
            return new ArrayList<>();
        }

        return dao.getHints(q.toLowerCase(), 10);
    }

}
