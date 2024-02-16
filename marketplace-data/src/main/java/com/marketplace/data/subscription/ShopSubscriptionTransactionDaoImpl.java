package com.marketplace.data.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.subscription.ShopSubscriptionTransaction;
import com.marketplace.domain.subscription.dao.ShopSubscriptionTransactionDao;

@Repository
public class ShopSubscriptionTransactionDaoImpl implements ShopSubscriptionTransactionDao {
	
	@Autowired
	private ShopSubscriptionTransactionRepo shopSubscriptionTransactionRepo;

	@Override
	public void save(ShopSubscriptionTransaction trans) {
		var entity = shopSubscriptionTransactionRepo.findById(trans.getInvoiceNo()).orElseGet(ShopSubscriptionTransactionEntity::new);
		entity.setInvoiceNo(trans.getInvoiceNo());
		entity.setMerchantId(trans.getMerchantId());
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
		
		shopSubscriptionTransactionRepo.save(entity);
		
	}
	
	@Override
	public ShopSubscriptionTransaction findByInvoiceNo(long invoiceNo) {
		return shopSubscriptionTransactionRepo.findByInvoiceNo(invoiceNo)
				.map(ShopSubscriptionTransactionMapper::toDomain)
				.orElse(null);
	}

}
