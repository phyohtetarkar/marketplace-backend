package com.shoppingcenter.app.batch.shop;

import java.util.ArrayList;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import com.shoppingcenter.search.shop.ShopDocument;

public class IndexShopWriter implements ItemWriter<ShopDocument> {

    private ElasticsearchOperations elasticsearchOperations;

    public IndexShopWriter(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void write(Chunk<? extends ShopDocument> chunk) throws Exception {
        var indexQueries = new ArrayList<IndexQuery>();
        for (var document : chunk) {
            var indexQuery = new IndexQueryBuilder()
                    .withIndex("shops")
                    .withId(String.valueOf(document.getId()))
                    .withObject(document)
                    .build();

            indexQueries.add(indexQuery);
        }

        elasticsearchOperations.bulkIndex(indexQueries, ShopDocument.class);
    }

}
