package com.shoppingcenter.data.subscription;

import com.shoppingcenter.domain.subscription.ShopSubscriptionTransaction;

public class ShopSubscriptionTransactionMapper {

	public static ShopSubscriptionTransaction toDomain(ShopSubscriptionTransactionEntity entity) {
		var trans = new ShopSubscriptionTransaction();
		trans.setId(entity.getId());
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
