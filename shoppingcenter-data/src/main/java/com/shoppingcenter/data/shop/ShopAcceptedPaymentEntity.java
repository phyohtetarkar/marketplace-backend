package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ShopAcceptedPayment")
@Table(name = Constants.TABLE_PREFIX + "shop_accepted_payment")
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
