package com.marketplace.domain.subscription;

import com.marketplace.domain.Utils;

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
