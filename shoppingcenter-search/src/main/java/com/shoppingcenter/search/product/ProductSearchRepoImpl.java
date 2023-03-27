package com.shoppingcenter.search.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.suggest.response.CompletionSuggestion;

import co.elastic.clients.elasticsearch.core.search.CompletionContext;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggester;
import co.elastic.clients.elasticsearch.core.search.Suggester;

public class ProductSearchRepoImpl implements ProductSearchRepoCustom {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    // @Override
    // public void setDiscount(List<Long> productIds, DiscountDocument discount) {
    // var nativeQuery = NativeQuery.builder().withQuery(q -> {
    // var terms = new TermsQuery.Builder().field("id")
    // .terms(tf -> tf.value(productIds.stream().map(FieldValue::of).toList()))
    // .build();
    // var boolQuery = new BoolQuery.Builder().must(mq -> mq.terms(terms));

    // return q.bool(boolQuery.build());
    // }).build();

    // var updateQuery = UpdateQuery.builder(nativeQuery).withIndex("products")
    // .withScript("ctx._source.discount = params.discount")
    // .withScriptType(ScriptType.INLINE)
    // .withLang("painless")
    // .withParams(Map.of("discount", discount))
    // .withBatchSize(100)
    // .build();

    // elasticsearchOperations.updateByQuery(updateQuery,
    // IndexCoordinates.of("products"));
    // }

    // @Override
    // public void removeDiscount(List<Long> productIds, long discountId) {
    // var nativeQuery = NativeQuery.builder().withQuery(q -> {
    // var nested = new NestedQuery.Builder()
    // .path("discount")
    // .query(nq -> nq.match(m -> m.field("discount.id").query(discountId)))
    // .build();

    // var boolQuery = new BoolQuery.Builder().must(mq -> mq.nested(nested));

    // if (productIds != null && productIds.size() > 0) {
    // var terms = new TermsQuery.Builder().field("id")
    // .terms(tf -> tf.value(productIds.stream().map(FieldValue::of).toList()))
    // .build();
    // boolQuery = boolQuery.must(mq -> mq.terms(terms));
    // }

    // return q.bool(boolQuery.build());
    // }).build();

    // var updateQuery = UpdateQuery.builder(nativeQuery)
    // .withIndex("products")
    // .withScript("ctx._source.remove('discount')")
    // .withScriptType(ScriptType.INLINE)
    // .withLang("painless")
    // .withBatchSize(100)
    // .build();

    // elasticsearchOperations.updateByQuery(updateQuery,
    // IndexCoordinates.of("products"));

    // }

    // @Override
    // public void updateCategory(CategoryDocument category) {
    // var categoryId = String.valueOf(category.getId());
    // var query = NativeQuery.builder().withQuery(q -> {
    // var nested = new NestedQuery.Builder()
    // .path("categories")
    // .query(nq -> nq.match(m -> m.field("categories.entityId").query(categoryId)))
    // .build();

    // return q.bool(bq -> bq.must(mq -> mq.nested(nested)));
    // }).build();

    // var script = """
    // int categoryId = params.category.id;
    // if (ctx._source.category.id == categoryId) {
    // ctx._source.category = params.category;
    // }
    // ctx._source.categories.removeIf(item -> item.id == categoryId);
    // ctx._source.categories.add(params.category);
    // """;

    // var updateQuery = UpdateQuery.builder(query)
    // .withIndex("products")
    // .withScript(script)
    // .withScriptType(ScriptType.INLINE)
    // .withLang("painless")
    // .withParams(Map.of("category", category))
    // .withBatchSize(100)
    // .build();

    // elasticsearchOperations.updateByQuery(updateQuery,
    // IndexCoordinates.of("products"));
    // }

    // @Override
    // public void updateShop(long shopId) {
    // var shopQuery = new CriteriaQueryBuilder(new
    // Criteria("id").is(shopId)).build();

    // var shopHit = elasticsearchOperations.searchOne(shopQuery,
    // ShopDocument.class);

    // var shop = shopHit.getContent();

    // if (shop != null) {
    // var query = NativeQuery.builder().withQuery(q -> {
    // var nested = new NestedQuery.Builder().path("shop")
    // .query(nq -> nq.match(m -> m.field("shop.id").query(shop.getId()))).build();

    // return q.bool(bq -> bq.must(mq -> mq.nested(nested)));
    // }).build();

    // var updateQuery = UpdateQuery.builder(query)
    // .withIndex("products")
    // .withScript("ctx._source.shop = params.shop")
    // .withScriptType(ScriptType.INLINE)
    // .withLang("painless").withParams(Map.of("shop", shop))
    // .withBatchSize(100)
    // .build();

    // elasticsearchOperations.updateByQuery(updateQuery,
    // IndexCoordinates.of("products"));
    // }

    // }

    @Override
    public List<String> findSuggestions(String query, int limit) {
        var suggesterKey = "product-suggest";
        var contexts = List.of(new CompletionContext.Builder().context(ctx -> ctx.category("PUBLISHED")).build());
        var completionSuggester = new CompletionSuggester.Builder()
                .field("suggest")
                .size(limit)
                .contexts("status", contexts)
                .build();
        var suggester = new Suggester.Builder()
                .suggesters(suggesterKey, fs -> fs.prefix(query).completion(completionSuggester))
                .build();
        var nativeQuery = NativeQuery.builder()
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("suggest").build())
                .withSuggester(suggester)
                .build();

        var searchHits = elasticsearchOperations.search(nativeQuery, ProductDocument.class);
        var suggestion = (CompletionSuggestion<?>) searchHits.getSuggest().getSuggestion(suggesterKey);
        return suggestion.getEntries().stream()
                .flatMap(en -> en.getOptions().stream())
                .map(op -> {
                    // if (op.getSearchHit().getContent() instanceof ProductDocument d) {
                    // return d.getName();
                    // }
                    return op.getText();
                })
                .toList();
    }

    @Override
    public List<ProductDocument> findAll(Query query) {
        var searchHits = elasticsearchOperations.search(query, ProductDocument.class);
        return searchHits.get().map(sh -> sh.getContent()).toList();
    }

    @Override
    public SearchPage<ProductDocument> findAll(Query query, Pageable pageable) {

        var searchHits = elasticsearchOperations.search(query,
                ProductDocument.class);

        var searchPage = SearchHitSupport.searchPageFor(searchHits, pageable);

        return searchPage;
    }

}
