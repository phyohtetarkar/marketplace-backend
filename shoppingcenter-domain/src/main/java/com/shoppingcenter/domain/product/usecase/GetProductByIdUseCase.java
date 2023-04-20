package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductByIdUseCase {

    private ProductDao dao;

    public GetProductByIdUseCase(ProductDao dao) {
        this.dao = dao;
    }

    public Product apply(long id) {
        var product = dao.findById(id);
//        if (product == null) {
//            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Product not found");
//        }
        return product;
    }

}
