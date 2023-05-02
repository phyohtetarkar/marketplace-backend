package com.shoppingcenter.app.batch.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.search.shop.ShopDocument;

public class IndexShopProcessor implements ItemProcessor<ShopEntity, ShopDocument> {

    @Override
    public ShopDocument process(ShopEntity item) throws Exception {
        var document = new ShopDocument();
        document.setId(item.getId());
        document.setName(item.getName());
        document.setSlug(item.getSlug());
        document.setHeadline(item.getHeadline());
        document.setCreatedAt(item.getCreatedAt());

        var splittedNames = Arrays.asList(document.getName().split("\\s+"));
        var splittedHeadlines = Arrays.asList(document.getHeadline().split("\\s+"));
        var lenN = splittedNames.size();
        var lenH = splittedHeadlines.size();
        var suggestInputs = new ArrayList<String>();

        for (var i = 0; i < lenN; i++) {
            var input = splittedNames.stream().skip(i).collect(Collectors.joining(" "));
            if (input.length() > 1) {
                suggestInputs.add(input);
            }
        }

        for (var i = 0; i < lenH; i++) {
            var input = splittedHeadlines.stream().skip(i).collect(Collectors.joining(" "));
            if (input.length() > 1) {
                suggestInputs.add(input);
            }
        }

        var suggest = new Completion(suggestInputs);
        document.setSuggest(suggest);
        return document;
    }

}
