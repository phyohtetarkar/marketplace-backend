package com.shoppingcenter.domain.product.usecase;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductDao;

public class GetRelatedProductsUseCaseImpl implements GetRelatedProductsUseCase {

    private ProductDao dao;

    public GetRelatedProductsUseCaseImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Product> apply(long productId, int size) {
        var product = dao.findById(productId);
        if (product == null) {
            return new ArrayList<>();
        }

        // long total = dao.countByIdNotAndCategory(productId, categoryId);
        // if (total <= 0) {
        // return new ArrayList<>();
        // }

        // var totalPage = total / size;

        // var page = totalPage > 1 ? (int) Math.floor(Math.random() * totalPage) : 0;
        // return dao.getRelatedProducts(productId, categoryId, PageQuery.of(page,
        // size));

        // TODO: implementation

        return new ArrayList<>();
    }

}
