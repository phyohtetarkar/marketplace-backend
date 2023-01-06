package com.shoppingcenter.data.order;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.shoppingcenter.data.AuditingEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double subTotalPrice;

    private double totalPrice;

    private double discount;

    private double deliveryFee;

    private int quantity;

    @Column(columnDefinition = "TEXT")
    private String note;

}
