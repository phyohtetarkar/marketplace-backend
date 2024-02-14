package com.marketplace.api.consumer.order;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetailDTO {

    private String accountType;

    private String receiptImage;
    
    private MultipartFile file;
    
}
