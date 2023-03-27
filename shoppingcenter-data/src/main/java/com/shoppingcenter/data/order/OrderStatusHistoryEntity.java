package com.shoppingcenter.data.order;

import com.shoppingcenter.data.AuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusHistoryEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Version
    private long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private OrderEntity order;
}
