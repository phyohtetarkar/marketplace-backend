package com.shoppingcenter.data.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.shop.dao.ShopSubscriptionTransactionDao;
import com.shoppingcenter.domain.subscription.ShopSubscriptionTransaction;

@Repository
public class ShopSubscriptionTransactionDaoImpl implements ShopSubscriptionTransactionDao {
	
	@Autowired
	private ShopSubscriptionRepo shopSubscriptionRepo;
	
	@Autowired
	private ShopSubscriptionTransactionRepo shopSubscriptionTransactionRepo;

	@Override
	public void save(ShopSubscriptionTransaction trans) {
		var entity = shopSubscriptionTransactionRepo.findById(trans.getShopSubscriptionId()).orElseGet(ShopSubscriptionTransactionEntity::new);
		entity.setMerchantId(trans.getMerchantId());
		entity.setInvoiceNo(trans.getInvoiceNo());
		entity.setCardNo(trans.getCardNo());
		entity.setAmount(trans.getAmount());
		entity.setCurrencyCode(trans.getCurrencyCode());
		entity.setReferenceNo(trans.getReferenceNo());
		entity.setAgentCode(trans.getAgentCode());
		entity.setChannelCode(trans.getChannelCode());
		entity.setApprovalCode(trans.getApprovalCode());
		entity.setEci(trans.getEci());
		entity.setTranRef(trans.getTranRef());
		entity.setTransactionDateTime(trans.getTransactionDateTime());
		entity.setRespCode(trans.getRespCode());
		entity.setRespDesc(trans.getRespDesc());
		
		entity.setShopSubscription(shopSubscriptionRepo.getReferenceById(trans.getShopSubscriptionId()));
		
		shopSubscriptionTransactionRepo.save(entity);
		
	}

}
