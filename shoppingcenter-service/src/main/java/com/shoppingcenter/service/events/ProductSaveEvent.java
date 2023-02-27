package com.shoppingcenter.service.events;

import com.shoppingcenter.service.product.model.Product;

import lombok.Getter;

@Getter
public class ProductSaveEvent extends DomainEvent {

    private Product product;

    public ProductSaveEvent(Object source, Product product) {
        super(source);
        this.product = product;
    }

}
