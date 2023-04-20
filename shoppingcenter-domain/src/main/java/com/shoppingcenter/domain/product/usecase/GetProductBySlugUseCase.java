package com.shoppingcenter.domain.product.usecase;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetProductBySlugUseCase {

    private ProductDao dao;

    public GetProductBySlugUseCase(ProductDao dao) {
        this.dao = dao;
    }

    public Product apply(String slug) {
        var product = dao.findBySlug(slug);

//        if (product == null) {
//            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Product not found");
//        }

        return product;
    }

}
