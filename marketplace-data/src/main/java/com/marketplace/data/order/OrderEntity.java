package com.marketplace.data.order;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.data.AuditingEntity;
import com.marketplace.data.shop.ShopEntity;
import com.marketplace.data.user.UserEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.order.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Order")
@Table(name = Constants.TABLE_PREFIX + "order")
public class OrderEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String orderCode;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal subTotalPrice;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal discountPrice;

    private int quantity;
    
    @Column(columnDefinition = "TEXT")
    private String note;

    @Enumerated(EnumType.STRING)
    private Order.Status status;

    @Enumerated(EnumType.STRING)
    private Order.PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderItemEntity> items;

    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private DeliveryDetailEntity delivery;

    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private PaymentDetailEntity payment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shop;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity customer;

    public OrderEntity() {
    }

}
