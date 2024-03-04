package com.marketplace.api.vendor.discount;

import java.math.BigDecimal;

import com.marketplace.api.AuditDTO;
import com.marketplace.domain.discount.Discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
	private long id;

	private String title;

	private BigDecimal value;

	private Discount.Type type;

	private AuditDTO audit;

}
