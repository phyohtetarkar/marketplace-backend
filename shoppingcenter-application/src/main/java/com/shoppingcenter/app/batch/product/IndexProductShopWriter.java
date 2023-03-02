package com.shoppingcenter.app.batch.product;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import com.shoppingcenter.search.product.ProductDocument;

public class IndexProductShopWriter implements ItemWriter<ProductDocument> {

    private ElasticsearchOperations elasticsearchOperations;

    public IndexProductShopWriter(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public void write(Chunk<? extends ProductDocument> chunk) throws Exception {
        var updateQueries = new ArrayList<UpdateQuery>();
        for (var document : chunk) {
            var shop = document.getShop();
            var updateQuery = UpdateQuery.builder(document.getId())
                    .withIndex("products")
                    .withDocument(Document.from(Map.of("shop", shop)))
                    .build();

            updateQueries.add(updateQuery);
        }

        elasticsearchOperations.bulkUpdate(updateQueries, ProductDocument.class);
    }

}
