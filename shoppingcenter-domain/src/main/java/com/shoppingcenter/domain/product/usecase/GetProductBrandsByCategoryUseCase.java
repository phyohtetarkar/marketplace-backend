package com.shoppingcenter.domain.product.usecase;

import java.util.List;

import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductBrandsByCategoryUseCase {

    private ProductDao dao;

    public GetProductBrandsByCategoryUseCase(ProductDao dao) {
        this.dao = dao;
    }

    public List<String> apply(String slug) {
        return dao.findProductBrandsByCategory(slug);
    }

    public List<String> apply(int id) {
        return dao.findProductBrandsByCategoryId(id);
    }

}
