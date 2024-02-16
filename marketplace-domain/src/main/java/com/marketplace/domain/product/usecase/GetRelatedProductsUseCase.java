package com.marketplace.domain.product.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class GetRelatedProductsUseCase {

	@Autowired
    private ProductDao dao;

	@Transactional(readOnly = true)
    public List<Product> apply(long productId, int size) {
        // var product = dao.findById(productId);
        // if (product == null) {
        // return new ArrayList<>();
        // }

        // long total = dao.countByIdNotAndCategory(productId, categoryId);
        // if (total <= 0) {
        // return new ArrayList<>();
        // }

        // var totalPage = total / size;

        // var page = totalPage > 1 ? (int) Math.floor(Math.random() * totalPage) : 0;
        return dao.getRelatedProducts(productId, PageQuery.of(0, size));
    }

}
