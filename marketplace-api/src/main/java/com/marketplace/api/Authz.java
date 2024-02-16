package com.marketplace.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.dao.ShopMemberDao;

@Component
public class Authz {
	
	@Autowired
	private ShopMemberDao shopMemberDao;

	public boolean isShopMember(Long shopId) {
		if (shopId == null || shopId == 0) {
			return false;
		}
		var userId = AuthenticationUtil.getAuthenticatedUserId();
		var result = shopMemberDao.existsByShopAndUser(shopId, userId);
		return result;
	}
}
