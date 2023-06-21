package com.shoppingcenter.domain.subscription;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubscriptionPromoQuery {
	
	private String code;
	
	private Boolean available;
	
	private Boolean used;
	
	private Boolean expired;

	private Integer page;
	
	public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
