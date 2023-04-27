package com.shoppingcenter.domain.order;

import java.util.List;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.user.User;

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

    private int quantity;
    
    private String note;

    private Order.Status status;

    private Order.PaymentMethod paymentMethod;

    private DeliveryDetail delivery;

    private PaymentDetail payment;

    private List<OrderItem> items;

    private User user;

    private Shop shop;

    private long createdAt;
}
