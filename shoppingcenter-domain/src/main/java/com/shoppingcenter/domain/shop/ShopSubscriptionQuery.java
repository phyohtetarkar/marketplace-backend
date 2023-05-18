package com.shoppingcenter.domain.shop;

import com.shoppingcenter.domain.Utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopSubscriptionQuery {

	private Long shopId;
	
	private Integer page;
	
	public Integer getPage() {
        return Utils.normalizePage(page);
    }
}
