package com.shoppingcenter.app.batch.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.search.product.ProductDocument;

public class IndexProductProcessor implements ItemProcessor<ProductEntity, ProductDocument> {

    @Override
    public ProductDocument process(ProductEntity item) throws Exception {
        var document = new ProductDocument();
        document.setId(item.getId());
        document.setName(item.getName());
        document.setSlug(item.getSlug());
        document.setBrand(item.getBrand());
        document.setPrice(item.getPrice());

        document.setCategory(CategoryMapper.toDocument(item.getCategory()));
        document.setShop(ShopMapper.toDocument(item.getShop()));

        var splittedNames = Arrays.asList(document.getName().split("\\s+"));
        var len = splittedNames.size();
        var suggestInputs = new ArrayList<String>();

        for (var i = 0; i < len; i++) {
            var input = splittedNames.stream().skip(i).collect(Collectors.joining(" "));
            if (input.length() > 1) {
                suggestInputs.add(input);
            }
        }

        var suggest = new Completion(suggestInputs);
        document.setSuggest(suggest);

        return document;
    }

}
