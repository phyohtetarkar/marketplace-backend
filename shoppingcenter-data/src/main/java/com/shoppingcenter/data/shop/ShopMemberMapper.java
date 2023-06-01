package com.shoppingcenter.data.shop;

import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.domain.shop.ShopMember;

public class ShopMemberMapper {

	public static ShopMember toDomain(ShopMemberEntity entity) {
		var member = new ShopMember();
		member.setRole(entity.getRole());
		member.setMember(UserMapper.toDomain(entity.getUser()));
		return member;
	}
	
}
