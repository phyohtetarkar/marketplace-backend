package com.marketplace.domain.order;

import com.marketplace.domain.UploadFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetail {

    private String accountType;

    private String receiptImage;
    
    private UploadFile file;
    
}
