package com.shoppingcenter.service.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DomainEventListener implements ApplicationListener<DomainEvent> {

    @Override
    public void onApplicationEvent(DomainEvent event) {
        if (event instanceof ProductSaveEvent evt) {

        }
    }

}
