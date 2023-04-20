package com.shoppingcenter.data.order;

import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.user.UserEntity;
import com.shoppingcenter.domain.order.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String orderCode;

    private double subTotalPrice;

    private double totalPrice;

    private double discount;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Order.Status status;

    @Enumerated(EnumType.STRING)
    private Order.PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderItemEntity> items;

    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private DeliveryDetailEntity detail;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private PaymentDetailEntity payment;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shop;

    public OrderEntity() {
    }

}
