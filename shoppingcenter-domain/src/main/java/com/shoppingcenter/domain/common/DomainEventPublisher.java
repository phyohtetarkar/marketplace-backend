package com.shoppingcenter.domain.common;

import com.shoppingcenter.domain.shop.Shop;

public interface DomainEventPublisher {

    void publishProductCategoryUpdate(long categoryId);

    void publishShopSave(Shop shop);

}
