package com.shoppingcenter.data.order;

import com.shoppingcenter.data.AuditingEntity;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetailEntity extends AuditingEntity {

    @Id
    private long id;

    private String accountType;

    private String paySlipImage;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private OrderEntity order;

}
