package com.shoppingcenter.app.controller.order.dto;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.modelmapper.TypeToken;

import com.shoppingcenter.app.controller.product.dto.ProductVariantAttributeDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

	private long id;

	private String productName;

	private String productSlug;

	private String productThumbnail;

	private BigDecimal unitPrice;

	private BigDecimal discount;

	private int quantity;

	private boolean cancelled;

	private Set<ProductVariantAttributeDTO> attributes;
	
	private long productId;
	
	public static Type listType() {
        return new TypeToken<List<OrderItemDTO>>() {
        }.getType();
    }

}
