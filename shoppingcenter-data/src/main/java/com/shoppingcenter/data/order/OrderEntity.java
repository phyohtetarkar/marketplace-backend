package com.shoppingcenter.data.order;

import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.customer.CustomerEntity;
import com.shoppingcenter.data.shop.ShopEntity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
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

    @Column(columnDefinition = "TEXT")
    private String note;

    @Version
    private long version;

    private String userId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ShopEntity shop;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> items;

    @OneToMany(mappedBy = "order")
    private List<OrderStatusHistoryEntity> statusHistories;

    public OrderEntity() {
    }

}
