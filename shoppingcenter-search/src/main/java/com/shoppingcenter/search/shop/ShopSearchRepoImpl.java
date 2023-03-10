package com.shoppingcenter.search.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;

public class ShopSearchRepoImpl implements ShopSearchRepoCustom {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<ShopDocument> findAll(Query query) {
        var searchHits = elasticsearchOperations.search(query, ShopDocument.class);
        return searchHits.get().map(sh -> sh.getContent()).toList();
    }

    @Override
    public SearchPage<ShopDocument> findAll(Query query, Pageable pageable) {
        var searchHits = elasticsearchOperations.search(query, ShopDocument.class);

        var searchPage = SearchHitSupport.searchPageFor(searchHits, pageable);

        return searchPage;
    }

}
