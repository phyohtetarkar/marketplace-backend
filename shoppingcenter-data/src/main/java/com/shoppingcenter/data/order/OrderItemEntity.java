package com.shoppingcenter.data.order;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;

    private String productImage;

    private double unitPrice;

    private double discount;

    private int quantity;

    /**
     * JSON string as [{ option: 'option', value: 'value' }]
     */
    private String variant;

    private long productId;

    private OrderEntity order;

    public double getTotalPrice() {
        return (unitPrice * quantity) - (discount * quantity);
    }
}
