package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.payment.PaymentResult;
import com.shoppingcenter.domain.shop.ShopSubscription;
import com.shoppingcenter.domain.shop.ShopSubscription.Status;
import com.shoppingcenter.domain.shop.ShopSubscriptionTransaction;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionTransactionDao;

import lombok.Setter;

@Setter
public class CompleteShopSubscriptionUseCase {
	
	private ShopDao shopDao;
	
	private ShopSubscriptionDao shopSubscriptionDao;
	
	private ShopSubscriptionTransactionDao shopSubscriptionTransactionDao;

	public void apply(PaymentResult result) {
		if (result == null) {
			return;
		}
		var trans = new ShopSubscriptionTransaction(result);
		
		var shopSubscription = shopSubscriptionDao.findById(trans.getShopSubscriptionId());
		
		if (shopSubscription == null) {
			return;
		}
		
		var shop = shopSubscription.getShop();
		
		if ("0000".equals(trans.getRespCode())) {
			shopSubscription.setStatus(ShopSubscription.Status.SUCCESS);
		} else {
			shopSubscription.setStatus(ShopSubscription.Status.FAILED);
		}
		
		shopSubscriptionDao.save(shopSubscription);
		
		shopSubscriptionTransactionDao.save(trans);
		
		var status = shopSubscription.getStatus();
		
		if (status == Status.SUCCESS) {
			shopDao.updateExpiredAt(shop.getId(), shopSubscription.getEndAt());
		}
		
		
	}
	
}
