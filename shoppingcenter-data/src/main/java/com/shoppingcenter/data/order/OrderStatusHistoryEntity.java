package com.shoppingcenter.data.order;

import com.shoppingcenter.data.AuditingEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusHistoryEntity extends AuditingEntity {

    private long id;

    private OrderEntity order;
}
