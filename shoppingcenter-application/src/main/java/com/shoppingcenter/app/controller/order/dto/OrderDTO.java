package com.shoppingcenter.app.controller.order.dto;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.domain.order.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

	private long id;

    private String orderCode;

    private BigDecimal subTotalPrice;

    private BigDecimal totalPrice;

    private BigDecimal discount;

    private int quantity;
    
    private String note;

    private Order.Status status;

    private Order.PaymentMethod paymentMethod;

    private DeliveryDetailDTO delivery;

    private PaymentDetailDTO payment;

    private List<OrderItemDTO> items;

    private UserDTO user;

    private ShopDTO shop;

    private long createdAt;
    
    public static Type pageType() {
		return new TypeToken<PageDataDTO<OrderDTO>>() {
		}.getType();
	}

}
