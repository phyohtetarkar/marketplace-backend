package com.shoppingcenter.app.batch.product;

import org.springframework.batch.item.ItemProcessor;

import com.shoppingcenter.search.product.ProductDocument;
import com.shoppingcenter.search.shop.ShopDocument;

public class IndexProductShopProcessor implements ItemProcessor<ProductDocument, ProductDocument> {

    private ShopDocument shop;

    public IndexProductShopProcessor(ShopDocument shop) {
        this.shop = shop;
    }

    @Override
    public ProductDocument process(ProductDocument item) throws Exception {
        if (shop == null) {
            return null;
        }

        item.setShop(shop);
        return item;
    }

}
