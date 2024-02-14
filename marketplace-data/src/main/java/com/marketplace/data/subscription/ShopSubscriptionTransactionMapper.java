package com.marketplace.data.subscription;

import com.marketplace.domain.subscription.ShopSubscriptionTransaction;

public interface ShopSubscriptionTransactionMapper {

	public static ShopSubscriptionTransaction toDomain(ShopSubscriptionTransactionEntity entity) {
		var trans = new ShopSubscriptionTransaction();
		trans.setInvoiceNo(entity.getInvoiceNo());
		trans.setMerchantId(entity.getMerchantId());
		trans.setReferenceNo(entity.getReferenceNo());
		trans.setEci(entity.getEci());
		trans.setAmount(entity.getAmount());
		trans.setCardNo(entity.getCardNo());
		trans.setAgentCode(entity.getAgentCode());
		trans.setChannelCode(entity.getChannelCode());
		trans.setApprovalCode(entity.getApprovalCode());
		trans.setCurrencyCode(entity.getCurrencyCode());
		trans.setTranRef(entity.getTranRef());
		trans.setTransactionDateTime(entity.getTransactionDateTime());
		trans.setRespCode(entity.getRespCode());
		trans.setRespDesc(entity.getRespDesc());
		return trans;
	}
	
}
