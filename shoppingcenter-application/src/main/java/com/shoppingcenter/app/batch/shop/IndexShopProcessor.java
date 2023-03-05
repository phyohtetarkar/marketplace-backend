package com.shoppingcenter.app.batch.shop;

import org.springframework.batch.item.ItemProcessor;

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
        document.setStatus(item.getStatus());
        document.setLogo(item.getLogo());
        return document;
    }

}
