package com.shoppingcenter.data.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopSubscriptionTransactionEntity {

    private long id;

    private String shopName;

    private double subTotalPrice;

    private double discount;

    private double totalPrice;

    private String paymentType;

    private String status;

    private long createdAt;

    private long shopId;

    private long subscriptionId;

}
