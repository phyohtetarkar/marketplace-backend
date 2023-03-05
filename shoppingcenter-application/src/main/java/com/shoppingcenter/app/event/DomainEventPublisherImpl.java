package com.shoppingcenter.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.shoppingcenter.domain.common.DomainEventPublisher;
import com.shoppingcenter.domain.shop.Shop;

@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void publishProductCategoryUpdate(long categoryId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void publishShopSave(Shop shop) {
        eventPublisher.publishEvent(new ShopSaveEvent(this, shop));
    }

}
