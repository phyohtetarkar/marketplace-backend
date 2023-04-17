package com.shoppingcenter.domain.product.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.dao.ProductSearchDao;

public class GetProductHintsUseCase {

    private ProductSearchDao dao;

    public GetProductHintsUseCase(ProductSearchDao dao) {
        this.dao = dao;
    }

    public List<String> apply(String q) {
        if (!Utils.hasText(q)) {
            return new ArrayList<>();
        }
        return dao.getSuggestions(q.toLowerCase(), 10);
    }

}
