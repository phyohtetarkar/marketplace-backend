package com.shoppingcenter.domain.shop.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;

public class GetShopHintsUseCase {

    private ShopSearchDao dao;

    public GetShopHintsUseCase(ShopSearchDao dao) {
        this.dao = dao;
    }

    public List<String> apply(String q) {
        if (!Utils.hasText(q)) {
            return new ArrayList<>();
        }

        return dao.getSuggestions(q.toLowerCase(), 10);
    }

}
