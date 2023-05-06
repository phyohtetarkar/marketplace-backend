package com.shoppingcenter.domain.order;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderQuery {

	private Long shopId;
	
	private Long userId;
	
	private String date;
	
	private Order.Status status;
	
	private String code;
	
	private String timeZone;
	
	private Integer page;
	
	public Integer getPage() {
        return Utils.normalizePage(page);
    }
	
}
