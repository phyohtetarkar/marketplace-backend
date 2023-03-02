package com.shoppingcenter.data.product.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ProductDeleteEvent extends ApplicationEvent {

    private long productId;

    public ProductDeleteEvent(Object source, long productId) {
        super(source);
        this.productId = productId;
    }

}
