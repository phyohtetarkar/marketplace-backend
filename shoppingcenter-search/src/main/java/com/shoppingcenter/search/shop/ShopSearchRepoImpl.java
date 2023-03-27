package com.shoppingcenter.search.shop;

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

public class ShopSearchRepoImpl implements ShopSearchRepoCustom {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<String> findSuggestions(String query, int limit) {
        var suggesterKey = "shop-suggest";
        var contexts = List.of(new CompletionContext.Builder().context(ctx -> ctx.category("ACTIVE")).build());
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

        var searchHits = elasticsearchOperations.search(nativeQuery, ShopDocument.class);
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
