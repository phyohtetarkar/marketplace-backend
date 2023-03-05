package com.shoppingcenter.app.batch.product;

import java.util.ArrayList;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import com.shoppingcenter.search.product.ProductDocument;

public class IndexProductWriter implements ItemWriter<ProductDocument> {

    private ElasticsearchOperations elasticsearchOperations;

    public IndexProductWriter(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void write(Chunk<? extends ProductDocument> chunk) throws Exception {
        var indexQueries = new ArrayList<IndexQuery>();
        for (var document : chunk) {
            var indexQuery = new IndexQueryBuilder()
                    .withIndex("products")
                    .withId(String.valueOf(document.getId()))
                    .withObject(document)
                    .build();

            indexQueries.add(indexQuery);

            // var shop = document.getShop();
            // var updateQuery = UpdateQuery.builder(String.valueOf(document.getId()))
            // .withIndex("products")
            // .withDocument(Document.from(Map.of("shop", shop)))
            // .build();

            // updateQueries.add(updateQuery);
        }

        // elasticsearchOperations.bulkUpdate(updateQueries, ProductDocument.class);
        elasticsearchOperations.bulkIndex(indexQueries, ProductDocument.class);
    }

}
