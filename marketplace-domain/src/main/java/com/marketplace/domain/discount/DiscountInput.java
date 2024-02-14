package com.marketplace.domain.discount;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountInput {

	private long id;

	private long shopId;

	private String title;

	private BigDecimal value;

	private Discount.Type type;

}
