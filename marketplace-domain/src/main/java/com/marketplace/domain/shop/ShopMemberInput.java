package com.marketplace.domain.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMemberInput {

	private long shopId;
	
	private long userId;
	
	private ShopMember.Role role;
	
}
