package com.shoppingcenter.service.events;

import lombok.Getter;

@Getter
public class ProductDeleteEvent extends DomainEvent {

    private long productId;

    public ProductDeleteEvent(Object source, long productId) {
        super(source);
        this.productId = productId;
    }

}
