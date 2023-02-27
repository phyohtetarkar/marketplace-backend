package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {

    private ProductDao dao;

    public GetProductByIdUseCaseImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public Product apply(long id) {
        Product product = dao.findById(id);
        if (product == null) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Product not found");
        }
        return product;
    }

}
