package com.shoppingcenter.domain.order;

import com.shoppingcenter.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetail {

	private long orderId;

    private String accountType;

    private String receiptImage;
    
    private UploadFile file;
    
}
