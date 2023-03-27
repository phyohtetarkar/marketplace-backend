package com.shoppingcenter.search.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;

public interface ProductSearchRepoCustom {

    List<String> findSuggestions(String query, int limit);

    List<ProductDocument> findAll(Query query);

    SearchPage<ProductDocument> findAll(Query query, Pageable pageable);

}
