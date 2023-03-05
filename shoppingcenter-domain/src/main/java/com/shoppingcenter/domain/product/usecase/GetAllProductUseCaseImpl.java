package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetAllProductUseCaseImpl implements GetAllProductUseCase {

    private ProductDao dao;

    public GetAllProductUseCaseImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public PageData<Product> apply(ProductQuery query) {
        return dao.getProducts(query);
    }

}
