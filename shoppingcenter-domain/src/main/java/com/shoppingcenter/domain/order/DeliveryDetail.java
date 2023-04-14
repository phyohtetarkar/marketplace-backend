package com.shoppingcenter.domain.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDetail {

    private long id;

    private String name;

    private String phone;

    private String address;

    private String note;

    private long orderId;

}
