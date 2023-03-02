package com.shoppingcenter.search.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

public class ProductSearchRepoImpl implements ProductSearchRepoCustom {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public void setDiscount(List<Long> productIds, DiscountDocument discount) {
        var criteria = new Criteria("entityId").in(productIds);
        var query = new CriteriaQueryBuilder(criteria).build();

        var updateQuery = UpdateQuery.builder(query)
                .withIndex("products")
                .withDocument(Document.from(Map.of("discount", discount)))
                .build();

        elasticsearchOperations.update(updateQuery, IndexCoordinates.of("products"));
    }

    @Override
    public List<ProductDocument> findAll(Query query) {
        var searchHits = elasticsearchOperations.search(query, ProductDocument.class);
        return searchHits.get().map(sh -> sh.getContent()).toList();
    }

    @Override
    public SearchPage<ProductDocument> findAll(Criteria criteria, Pageable pageable) {

        var searchHits = elasticsearchOperations.search(new CriteriaQuery(criteria, pageable), ProductDocument.class);

        var searchPage = SearchHitSupport.searchPageFor(searchHits, pageable);

        return searchPage;
    }

}
