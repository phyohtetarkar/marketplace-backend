package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.misc.dto.CityDTO;
import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.shop.usecase.GetAllShopDeliveryCityUseCase;
import com.shoppingcenter.domain.shop.usecase.SaveShopDeliveryCityUseCase;

@Facade
public class ShopDeliveryCityFacade {

	@Autowired
	private SaveShopDeliveryCityUseCase saveShopDeliveryCityUseCase;
	
	@Autowired
	private GetAllShopDeliveryCityUseCase getAllShopDeliveryCityUseCase;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public void save(long shopId, List<CityDTO> cities) {
		var list = cities.stream().map(c -> modelMapper.map(c, City.class)).toList();
		saveShopDeliveryCityUseCase.apply(shopId, list);
	}
	
	public List<CityDTO> findByShop(long shopId) {
		return modelMapper.map(getAllShopDeliveryCityUseCase.apply(shopId), CityDTO.listType());
	}
	
}
