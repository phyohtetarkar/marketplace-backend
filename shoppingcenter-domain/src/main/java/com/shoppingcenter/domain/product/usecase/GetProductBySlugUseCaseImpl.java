package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductBySlugUseCaseImpl implements GetProductBySlugUseCase {

    private ProductDao dao;

    public GetProductBySlugUseCaseImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public Product apply(String slug) {
        Product product = dao.findBySlug(slug);

        if (product == null) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Product not found");
        }

        return product;
    }

}
