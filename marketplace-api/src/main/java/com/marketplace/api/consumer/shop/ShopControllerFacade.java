package com.marketplace.api.consumer.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.vendor.shop.ShopAcceptedPaymentDTO;
import com.marketplace.domain.shop.ShopQuery;
import com.marketplace.domain.shop.usecase.GetAllShopAcceptedPaymentUseCase;
import com.marketplace.domain.shop.usecase.GetAllShopUseCase;
import com.marketplace.domain.shop.usecase.GetShopBySlugUseCase;
import com.marketplace.domain.shop.usecase.GetShopSettingUseCase;

@Component
public class ShopControllerFacade {

	@Autowired
	private GetShopBySlugUseCase getShopBySlugUseCase;

	@Autowired
	private GetAllShopUseCase getAllShopUseCase;
	
	@Autowired
	private GetShopSettingUseCase getShopSettingUseCase;
	
	@Autowired
	private GetAllShopAcceptedPaymentUseCase getAllShopAcceptedPaymentUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;

	public ShopDTO findBySlug(String slug) {
		var source = getShopBySlugUseCase.apply(slug);
		return mapper.map(source);
	}
	
	public ShopSettingDTO getShopSetting(long shopId) {
    	var result = getShopSettingUseCase.apply(shopId);
    	return mapper.map(result);
    }

	public PageDataDTO<ShopDTO> findAll(ShopQuery query) {
		var source = getAllShopUseCase.apply(query);
		return mapper.mapShopPage(source);
	}
	
	public List<ShopAcceptedPaymentDTO> findAcceptedPaymentsByShop(long shopId) {
    	var source = getAllShopAcceptedPaymentUseCase.apply(shopId);
    	return mapper.mapShopAcceptedPaymentList(source);
    }

}
