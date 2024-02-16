package com.marketplace.data.shop;

import com.marketplace.data.user.UserMapper;
import com.marketplace.domain.shop.ShopMember;

public interface ShopMemberMapper {

	public static ShopMember toDomain(ShopMemberEntity entity) {
		var member = new ShopMember();
		member.setRole(entity.getRole());
		member.setMember(UserMapper.toDomain(entity.getUser()));
		return member;
	}
	
}
