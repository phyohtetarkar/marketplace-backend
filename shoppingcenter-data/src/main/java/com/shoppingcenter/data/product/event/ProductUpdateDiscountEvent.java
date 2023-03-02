package com.shoppingcenter.data.product.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.shoppingcenter.domain.discount.Discount;

import lombok.Getter;

@Getter
public class ProductUpdateDiscountEvent extends ApplicationEvent {

    private List<Long> productIds;

    private Discount discount;

    public ProductUpdateDiscountEvent(Object source, List<Long> productIds, Discount discount) {
        super(source);
        this.productIds = productIds;
        this.discount = discount;
    }

}
