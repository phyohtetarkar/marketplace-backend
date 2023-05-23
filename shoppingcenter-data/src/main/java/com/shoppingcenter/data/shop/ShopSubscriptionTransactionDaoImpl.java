package com.shoppingcenter.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.shop.ShopSubscriptionTransaction;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionTransactionDao;

@Repository
public class ShopSubscriptionTransactionDaoImpl implements ShopSubscriptionTransactionDao {
	
	@Autowired
	private ShopSubscriptionRepo shopSubscriptionRepo;
	
	@Autowired
	private ShopSubscriptionTransactionRepo shopSubscriptionTransactionRepo;

	@Override
	public void save(ShopSubscriptionTransaction trans) {
		var entity = new ShopSubscriptionTransactionEntity();
		entity.setMerchantId(trans.getMerchantId());
		entity.setInvoiceNo(trans.getInvoiceNo());
		entity.setCardNo(trans.getCardNo());
		entity.setAmount(trans.getAmount());
		entity.setCurrencyCode(trans.getCurrencyCode());
		entity.setReferenceNo(trans.getReferenceNo());
		entity.setApprovalCode(trans.getApprovalCode());
		entity.setEci(trans.getEci());
		entity.setTranRef(trans.getTranRef());
		entity.setRespCode(trans.getRespCode());
		entity.setRespDesc(trans.getRespDesc());
		
		entity.setShopSubscription(shopSubscriptionRepo.getReferenceById(trans.getShopSubscriptionId()));
		
		shopSubscriptionTransactionRepo.save(entity);
		
	}

}
