package com.marketplace.api.consumer.order;

import java.math.BigDecimal;
import java.util.List;

import com.marketplace.api.AuditDTO;
import com.marketplace.api.consumer.shop.ShopDTO;
import com.marketplace.api.consumer.user.UserDTO;
import com.marketplace.domain.order.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

	private long id;

    private String orderCode;

    private BigDecimal subTotalPrice;

    private BigDecimal totalPrice;

    private BigDecimal discountPrice;

    private int quantity;
    
    private String note;

    private Order.Status status;

    private Order.PaymentMethod paymentMethod;

    private DeliveryDetailDTO delivery;

    private PaymentDetailDTO payment;

    private List<OrderItemDTO> items;

    private UserDTO customer;

    private ShopDTO shop;

    private AuditDTO audit;
}
