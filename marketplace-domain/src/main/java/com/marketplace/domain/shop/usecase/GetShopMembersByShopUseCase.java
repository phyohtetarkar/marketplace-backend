package com.marketplace.domain.shop.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.shop.dao.ShopMemberDao;

@Component
public class GetShopMembersByShopUseCase {
	
	@Autowired
	private ShopMemberDao shopMemberDao;

	public List<ShopMember> apply(long shopId) {
		return shopMemberDao.findByShop(shopId);
	}
	
}
