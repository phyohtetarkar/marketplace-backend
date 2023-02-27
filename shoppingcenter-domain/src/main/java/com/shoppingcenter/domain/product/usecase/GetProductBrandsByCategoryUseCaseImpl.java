package com.shoppingcenter.domain.product.usecase;

import java.util.List;

import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductBrandsByCategoryUseCaseImpl implements GetProductBrandsByCategoryUseCase {

    private ProductDao dao;

    public GetProductBrandsByCategoryUseCaseImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public List<String> apply(String slug) {
        return dao.findProductBrandsByCategory(slug);
    }

}
