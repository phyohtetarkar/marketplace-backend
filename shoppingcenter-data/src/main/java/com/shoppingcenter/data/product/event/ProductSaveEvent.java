package com.shoppingcenter.data.product.event;

import org.springframework.context.ApplicationEvent;

import com.shoppingcenter.domain.product.Product;

import lombok.Getter;

@Getter
public class ProductSaveEvent extends ApplicationEvent {

    private Product product;

    public ProductSaveEvent(Object source, Product product) {
        super(source);
        this.product = product;
    }

}
