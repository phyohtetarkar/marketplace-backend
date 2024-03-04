package com.marketplace.api.vendor.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.vendor.VendorDataMapper;
import com.marketplace.domain.payment.PaymentTokenResponse;
import com.marketplace.domain.subscription.usecase.GetActiveSubscriptionsByShopUseCase;
import com.marketplace.domain.subscription.usecase.GetShopSubscriptionByInvoiceNoUseCase;
import com.marketplace.domain.subscription.usecase.RenewShopSubscriptionUseCase;

@Component
public class ShopSubscriptionControllerFacade {

	@Autowired
	private GetActiveSubscriptionsByShopUseCase getActiveSubscriptionsByShopUseCase;
	
	@Autowired
	private RenewShopSubscriptionUseCase renewShopSubscriptionUseCase;
	
	@Autowired
    private GetShopSubscriptionByInvoiceNoUseCase getShopSubscriptionByInvoiceNoUseCase;
	
	@Autowired
	private VendorDataMapper mapper;
	
	public PaymentTokenResponse renewSubscription(RenewSubscriptionDTO values) {
		return renewShopSubscriptionUseCase.apply(mapper.map(values));
	}
	
	public List<ShopSubscriptionDTO> getActiveShopSubscriptions(long shopId) {
		return mapper.mapShopSubscriptionList(getActiveSubscriptionsByShopUseCase.apply(shopId));
	}
	
	public ShopSubscriptionDTO getShopSubscription(long invoiceNo) {
    	var source = getShopSubscriptionByInvoiceNoUseCase.apply(invoiceNo);
    	return mapper.map(source);
    }
	
}
