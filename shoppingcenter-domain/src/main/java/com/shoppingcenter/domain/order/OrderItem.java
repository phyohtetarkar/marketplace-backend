package com.shoppingcenter.domain.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {

    private long id;
    
    private long orderId;

    private long productId;

    private String productName;
    
    private String productSlug;

    private String productImage;

    private double unitPrice;

    private double discount;

    private int quantity;

    private boolean removed;

    private String variant;
    
    public double getSubTotalPrice() {
        return (unitPrice * quantity);
    }

    public double getTotalPrice() {
        return (unitPrice * quantity) - (discount * quantity);
    }

}
