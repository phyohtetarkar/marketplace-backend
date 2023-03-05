package com.shoppingcenter.search.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import com.shoppingcenter.search.shop.ShopDocument;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;

public class ProductSearchRepoImpl implements ProductSearchRepoCustom {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public void setDiscount(List<Long> productIds, DiscountDocument discount) {
        var nativeQuery = NativeQuery.builder().withQuery(q -> {
            var terms = new TermsQuery.Builder().field("id")
                    .terms(tf -> tf.value(productIds.stream().map(FieldValue::of).toList()))
                    .build();
            var boolQuery = new BoolQuery.Builder().must(mq -> mq.terms(terms));

            return q.bool(boolQuery.build());
        }).build();

        var updateQuery = UpdateQuery.builder(nativeQuery).withIndex("products")
                .withScript("ctx._source.discount = params.discount")
                .withScriptType(ScriptType.INLINE)
                .withLang("painless")
                .withParams(Map.of("discount", discount))
                .withBatchSize(100)
                .build();

        elasticsearchOperations.updateByQuery(updateQuery, IndexCoordinates.of("products"));
    }

    @Override
    public void removeDiscount(List<Long> productIds, long discountId) {
        var nativeQuery = NativeQuery.builder().withQuery(q -> {
            var nested = new NestedQuery.Builder()
                    .path("discount")
                    .query(nq -> nq.match(m -> m.field("discount.id").query(discountId)))
                    .build();

            var boolQuery = new BoolQuery.Builder().must(mq -> mq.nested(nested));

            if (productIds != null && productIds.size() > 0) {
                var terms = new TermsQuery.Builder().field("id")
                        .terms(tf -> tf.value(productIds.stream().map(FieldValue::of).toList()))
                        .build();
                boolQuery = boolQuery.must(mq -> mq.terms(terms));
            }

            return q.bool(boolQuery.build());
        }).build();

        var updateQuery = UpdateQuery.builder(nativeQuery)
                .withIndex("products")
                .withScript("ctx._source.remove('discount')")
                .withScriptType(ScriptType.INLINE)
                .withLang("painless")
                .withBatchSize(100)
                .build();

        elasticsearchOperations.updateByQuery(updateQuery, IndexCoordinates.of("products"));

    }

    @Override
    public void updateCategory(CategoryDocument category) {
        var categoryId = String.valueOf(category.getId());
        var query = NativeQuery.builder().withQuery(q -> {
            var nested = new NestedQuery.Builder()
                    .path("categories")
                    .query(nq -> nq.match(m -> m.field("categories.entityId").query(categoryId)))
                    .build();

            return q.bool(bq -> bq.must(mq -> mq.nested(nested)));
        }).build();

        var script = """
                        int categoryId = params.category.id;
                        if (ctx._source.category.id == categoryId) {
                            ctx._source.category = params.category;
                        }
                        ctx._source.categories.removeIf(item -> item.id == categoryId);
                        ctx._source.categories.add(params.category);
                """;

        var updateQuery = UpdateQuery.builder(query)
                .withIndex("products")
                .withScript(script)
                .withScriptType(ScriptType.INLINE)
                .withLang("painless")
                .withParams(Map.of("category", category))
                .withBatchSize(100)
                .build();

        elasticsearchOperations.updateByQuery(updateQuery, IndexCoordinates.of("products"));
    }

    @Override
    public void updateShop(long shopId) {
        var shopQuery = new CriteriaQueryBuilder(new Criteria("id").is(shopId)).build();

        var shopHit = elasticsearchOperations.searchOne(shopQuery, ShopDocument.class);

        var shop = shopHit.getContent();

        if (shop != null) {
            var query = NativeQuery.builder().withQuery(q -> {
                var nested = new NestedQuery.Builder().path("shop")
                        .query(nq -> nq.match(m -> m.field("shop.id").query(shop.getId()))).build();

                return q.bool(bq -> bq.must(mq -> mq.nested(nested)));
            }).build();

            var updateQuery = UpdateQuery.builder(query)
                    .withIndex("products")
                    .withScript("ctx._source.shop = params.shop")
                    .withScriptType(ScriptType.INLINE)
                    .withLang("painless").withParams(Map.of("shop", shop))
                    .withBatchSize(100)
                    .build();

            elasticsearchOperations.updateByQuery(updateQuery, IndexCoordinates.of("products"));
        }

    }

    @Override
    public List<ProductDocument> findAll(Query query) {
        var searchHits = elasticsearchOperations.search(query, ProductDocument.class);
        return searchHits.get().map(sh -> sh.getContent()).toList();
    }

    @Override
    public SearchPage<ProductDocument> findAll(Criteria criteria, Pageable pageable) {

        var searchHits = elasticsearchOperations.search(new CriteriaQuery(criteria, pageable),
                ProductDocument.class);

        var searchPage = SearchHitSupport.searchPageFor(searchHits, pageable);

        return searchPage;
    }

}