package com.shoppingcenter.data.shop;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionTransactionEntity {

    private long id;

    private String shopName;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subTotalPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    private String paymentType;

    private String status;

    private long createdAt;

    private long shopId;

    private long subscriptionId;

}
