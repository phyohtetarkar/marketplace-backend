package com.marketplace.api.vendor.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.domain.payment.PaymentTokenResponse;
import com.marketplace.domain.subscription.RenewShopSubscriptionInput;
import com.marketplace.domain.subscription.usecase.GetActiveSubscriptionsByShopUseCase;
import com.marketplace.domain.subscription.usecase.GetShopSubscriptionByInvoiceNoUseCase;
import com.marketplace.domain.subscription.usecase.RenewShopSubscriptionUseCase;

@Component
public class ShopSubscriptionControllerFacade extends AbstractControllerFacade {

	@Autowired
	private GetActiveSubscriptionsByShopUseCase getActiveSubscriptionsByShopUseCase;
	
	@Autowired
	private RenewShopSubscriptionUseCase renewShopSubscriptionUseCase;
	
	@Autowired
    private GetShopSubscriptionByInvoiceNoUseCase getShopSubscriptionByInvoiceNoUseCase;
	
	public PaymentTokenResponse renewSubscription(RenewSubscriptionDTO values) {
		return renewShopSubscriptionUseCase.apply(map(values, RenewShopSubscriptionInput.class));
	}
	
	public List<ShopSubscriptionDTO> getActiveShopSubscriptions(long shopId) {
		return map(getActiveSubscriptionsByShopUseCase.apply(shopId), ShopSubscriptionDTO.listType());
	}
	
	public ShopSubscriptionDTO getShopSubscription(long invoiceNo) {
    	var source = getShopSubscriptionByInvoiceNoUseCase.apply(invoiceNo);
    	return map(source, ShopSubscriptionDTO.class);
    }
	
}
