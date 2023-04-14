package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.AuditingEntity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAcceptedPaymentEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String accountType;

    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shop;

    public ShopAcceptedPaymentEntity() {
    }
}
