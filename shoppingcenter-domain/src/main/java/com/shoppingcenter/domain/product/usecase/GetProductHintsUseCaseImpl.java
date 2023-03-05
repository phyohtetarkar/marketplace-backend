package com.shoppingcenter.domain.product.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductSearchDao;

public class GetProductHintsUseCaseImpl implements GetProductHintsUseCase {

    private ProductSearchDao dao;

    public GetProductHintsUseCaseImpl(ProductSearchDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Product> apply(String q) {
        if (!Utils.hasText(q)) {
            return new ArrayList<>();
        }
        return dao.getHints(q.toLowerCase(), 8);
    }

}
