package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.shop.dto.RenewSubscriptionDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSubscriptionDTO;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;
import com.shoppingcenter.domain.shop.dao.ShopSubscriptionDao;
import com.shoppingcenter.domain.subscription.RenewShopSubscriptionInput;
import com.shoppingcenter.domain.subscription.ShopSubscriptionQuery;
import com.shoppingcenter.domain.subscription.usecase.GetAllShopSubscriptionUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetCurrentSubscriptionByShopUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetPreSubscriptionsByShopUseCase;
import com.shoppingcenter.domain.subscription.usecase.RemoveUnprocessedSubscriptionsUseCase;
import com.shoppingcenter.domain.subscription.usecase.RenewShopSubscriptionUseCase;

@Service
public class ShopSubscriptionService {

	@Autowired
	private GetCurrentSubscriptionByShopUseCase getCurrentSubscriptionByShopUseCase;
	
	@Autowired
	private GetPreSubscriptionsByShopUseCase getPreSubscriptionsByShopUseCase;
	
	@Autowired
	private RenewShopSubscriptionUseCase renewShopSubscriptionUseCase;
	
	@Autowired
	private RemoveUnprocessedSubscriptionsUseCase removeUnprocessedSubscriptionsUseCase;
	
	@Autowired
	private GetAllShopSubscriptionUseCase getAllShopSubscriptionUseCase;
	
	@Autowired
	private ShopSubscriptionDao shopSubscriptionDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public void removeUnprocessedSubscriptions() {
		removeUnprocessedSubscriptionsUseCase.apply();
	}
	
	@Transactional
	public PaymentTokenResponse renewSubscription(RenewSubscriptionDTO data) {
		return renewShopSubscriptionUseCase.apply(modelMapper.map(data, RenewShopSubscriptionInput.class));
	}
	
	@Transactional(readOnly = true)
	public ShopSubscriptionDTO getCurrentSubscription(long shopId) {
		var source = getCurrentSubscriptionByShopUseCase.apply(shopId);
		
		if (source != null) {
			return modelMapper.map(source, ShopSubscriptionDTO.class);
		}
		
		return null;
	}
	
	@Transactional(readOnly = true)
	public ShopSubscriptionDTO getSubscription(long invoiceNo) {
		var source = shopSubscriptionDao.findById(invoiceNo);
		if (source != null) {
			return modelMapper.map(source, ShopSubscriptionDTO.class);
		}
		
		return null;
	}
	
	public List<ShopSubscriptionDTO> getPreSubscriptions(long shopId) {
		return modelMapper.map(getPreSubscriptionsByShopUseCase.apply(shopId), ShopSubscriptionDTO.listType());
	}
	
	@Transactional(readOnly = true)
	public PageDataDTO<ShopSubscriptionDTO> findAllShopSubscriptions(ShopSubscriptionQuery query) {
		var source = getAllShopSubscriptionUseCase.apply(query);
		return modelMapper.map(source, ShopSubscriptionDTO.pageType());
	}
	
}
