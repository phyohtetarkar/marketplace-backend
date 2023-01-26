package com.shoppingcenter.data.order;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.shop.ShopEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntity extends AuditingEntity {

    public enum Status {
        PENDING, CONFIRMED, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String orderCode;

    private double subTotalPrice;

    private double totalPrice;

    private double discount;

    private double deliveryFee;

    private int quantity;

    private String status;

    private String customerName;

    private String customerPhone;

    private String customerAddress;

    @Column(columnDefinition = "TEXT")
    private String note;

    private String userId;

    private ShopEntity shop;

    public OrderEntity() {
    }

}
