package com.shoppingcenter.domain.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetail {

    private long id;

    private String accountType;

    private String paySlipImage;

    private long orderId;
}
