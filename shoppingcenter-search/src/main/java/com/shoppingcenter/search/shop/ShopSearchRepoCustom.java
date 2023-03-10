package com.shoppingcenter.search.shop;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;

public interface ShopSearchRepoCustom {

    List<ShopDocument> findAll(Query query);

    SearchPage<ShopDocument> findAll(Query query, Pageable pageable);

}
