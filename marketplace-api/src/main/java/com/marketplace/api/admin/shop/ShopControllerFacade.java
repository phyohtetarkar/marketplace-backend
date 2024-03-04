package com.marketplace.api.admin.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.api.vendor.shop.ShopMemberDTO;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopQuery;
import com.marketplace.domain.shop.usecase.GetAllShopUseCase;
import com.marketplace.domain.shop.usecase.GetShopByIdUseCase;
import com.marketplace.domain.shop.usecase.GetShopMembersByShopUseCase;
import com.marketplace.domain.shop.usecase.UpdateShopFeaturedUseCase;
import com.marketplace.domain.shop.usecase.UpdateShopStatusUseCase;

@Component
public class ShopControllerFacade {
	
	@Autowired
	private UpdateShopStatusUseCase updateShopStatusUseCase;
	
	@Autowired
	private UpdateShopFeaturedUseCase updateShopFeaturedUseCase;

	@Autowired
	private GetShopByIdUseCase getShopByIdUseCase;
	
	@Autowired
	private GetShopMembersByShopUseCase getShopMembersByShopUseCase;

	@Autowired
	private GetAllShopUseCase getAllShopUseCase;
	
	@Autowired
    private AdminDataMapper mapper;
	
	public void updateStatus(long shopId, Shop.Status status) {
		updateShopStatusUseCase.apply(shopId, status);
	}
	
	public void updateFeatured(long shopId, boolean value) {
		updateShopFeaturedUseCase.apply(shopId, value);
	}

	public ShopDTO findById(long id) {
		var source = getShopByIdUseCase.apply(id);
		return mapper.map(source);
	}
	
	public List<ShopMemberDTO> getShopMembers(long shopId) {
		var source = getShopMembersByShopUseCase.apply(shopId);
		return mapper.mapShopMemberList(source);
	}

	public PageDataDTO<ShopDTO> findAll(ShopQuery query) {
		var source = getAllShopUseCase.apply(query);
		return mapper.mapShopPage(source);
	}

}
