package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetAllProductUseCase {

    private ProductDao dao;

    public GetAllProductUseCase(ProductDao dao) {
        this.dao = dao;
    }

    public PageData<Product> apply(ProductQuery query) {
        return dao.getProducts(query);
    }

}
