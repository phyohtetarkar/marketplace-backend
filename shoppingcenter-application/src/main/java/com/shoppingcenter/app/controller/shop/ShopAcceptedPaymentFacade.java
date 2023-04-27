package com.shoppingcenter.app.controller.shop;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.shop.dto.ShopAcceptedPaymentDTO;
import com.shoppingcenter.domain.shop.ShopAcceptedPayment;
import com.shoppingcenter.domain.shop.usecase.DeleteShopAcceptedPaymentUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopAcceptedPaymentUseCase;
import com.shoppingcenter.domain.shop.usecase.SaveShopAcceptedPaymentUseCase;

@Facade
public class ShopAcceptedPaymentFacade {

	@Autowired
	private SaveShopAcceptedPaymentUseCase saveShopAcceptedPaymentUseCase;
	
	@Autowired
	private DeleteShopAcceptedPaymentUseCase deleteShopAcceptedPaymentUseCase;
	
	@Autowired
	private GetAllShopAcceptedPaymentUseCase getAllShopAcceptedPaymentUseCase;
	
	@Autowired
    private ModelMapper modelMapper;

	public void save(ShopAcceptedPaymentDTO payment) {
		var model = modelMapper.map(payment, ShopAcceptedPayment.class);
    	saveShopAcceptedPaymentUseCase.apply(payment.getShopId(), Arrays.asList(model));
    }
    
    public void delete(long id) {
    	deleteShopAcceptedPaymentUseCase.apply(id);
    }
    
    public List<ShopAcceptedPaymentDTO> findAllByShop(long shopId) {
    	var source = getAllShopAcceptedPaymentUseCase.apply(shopId);
    	return modelMapper.map(source, ShopAcceptedPaymentDTO.listType());
    }
	
}
