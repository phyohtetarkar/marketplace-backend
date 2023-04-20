package com.shoppingcenter.data.order;

import com.shoppingcenter.data.AuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDetailEntity extends AuditingEntity {

    @Id
    private long id;

    private String name;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "TEXT")
    private String note;

    @MapsId("id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private OrderEntity order;

}
