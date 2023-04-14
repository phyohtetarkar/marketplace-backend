package com.shoppingcenter.domain.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    public enum Status {
        PENDING, CONFIRMED, COMPLETED, CANCELLED
    }

    public enum PaymentMethod {
        COD, BANK_TRANSFER
    }

    private long id;

    private String orderCode;

    private double subTotalPrice;

    private double totalPrice;

    private double discount;

    private double deliveryFee;

    private int quantity;

    private Order.Status status;

    private Order.PaymentMethod paymentMethod;

    private DeliveryDetail deliveryDetail;

    private PaymentDetail paymentDetail;

    private List<OrderItem> items;

    private String userId;

    private long shopId;

    private Long createdAt;
}
