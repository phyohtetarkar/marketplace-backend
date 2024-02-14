package com.marketplace.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopMemberDao;

@Component
public class Authz {
	
	@Autowired
	private ShopMemberDao shopMemberDao;
	
	@Autowired
	private ShopDao shopDao;

	public boolean isShopMember(Long shopId) {
		if (shopId == null || shopId == 0) {
			return false;
		}
		var userId = AuthenticationUtil.getAuthenticatedUserId();
		var result = shopMemberDao.existsByShopAndUser(shopId, userId);
		return result;
	}
	
	public boolean isShopExpired(Long shopId) {
		if (shopId == null || shopId == 0) {
			return false;
		}
		var currentTime = System.currentTimeMillis();
		var expiredAt = shopDao.getExpiredAt(shopId);
		return expiredAt < currentTime;
	}
}
