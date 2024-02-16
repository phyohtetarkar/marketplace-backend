package com.marketplace.domain.subscription;

import com.marketplace.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopSubscriptionQuery {

	private Long shopId;
	
	private String fromDate;
	
	private String toDate;
	
	private ShopSubscription.Status status;
	
	private String timeZone;
	
	private Integer page;
	
	public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
