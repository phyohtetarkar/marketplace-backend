package com.marketplace.domain.product.usecase;

import java.util.ArrayList;
import java.util.List;

import com.marketplace.domain.Utils;
import com.marketplace.domain.product.dao.ProductSearchDao;

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
