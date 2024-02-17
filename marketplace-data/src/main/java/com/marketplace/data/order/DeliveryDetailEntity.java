package com.marketplace.data.order;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "DeliveryDetail")
@Table(name = Constants.TABLE_PREFIX + "order_delivery_detail")
public class DeliveryDetailEntity extends AuditingEntity {

    @Id
    private long id;

    private String name;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private OrderEntity order;

}
