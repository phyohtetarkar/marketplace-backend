package com.shoppingcenter.app.controller.order.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetailDTO {

	private long orderId;

    private String accountType;

    private String receiptImage;
    
    private MultipartFile file;
    
}
