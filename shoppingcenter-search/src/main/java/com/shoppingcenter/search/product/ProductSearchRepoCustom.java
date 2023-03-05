package com.shoppingcenter.search.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.Query;

public interface ProductSearchRepoCustom {

    void setDiscount(List<Long> productIds, DiscountDocument discount);

    void removeDiscount(List<Long> productIds, long discountId);

    void updateCategory(CategoryDocument category);

    void updateShop(long shopId);

    List<ProductDocument> findAll(Query query);

    SearchPage<ProductDocument> findAll(Criteria criteria, Pageable pageable);

}
