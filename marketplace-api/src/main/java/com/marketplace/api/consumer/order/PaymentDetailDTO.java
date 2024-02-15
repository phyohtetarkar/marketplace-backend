package com.marketplace.api.consumer.order;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.OrderImageSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetailDTO {

    private String accountType;

    @JsonSerialize(using = OrderImageSerializer.class)
    private String receiptImage;
    
    private MultipartFile file;
    
}
