package com.shoppingcenter.domain.product.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductHintsUseCaseImpl implements GetProductHintsUseCase {

    private ProductDao dao;

    public GetProductHintsUseCaseImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Product> apply(String q) {
        if (!Utils.hasText(q)) {
            return new ArrayList<>();
        }
        String ql = "%" + q.toLowerCase() + "%";
        return dao.findProductByNameOrBrandLimit(ql, 8);
    }

}
