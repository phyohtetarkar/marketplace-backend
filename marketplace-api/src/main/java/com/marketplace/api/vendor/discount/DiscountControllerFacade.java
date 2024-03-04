package com.marketplace.api.vendor.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.vendor.VendorDataMapper;
import com.marketplace.domain.discount.usecase.ApplyDiscountsUseCase;
import com.marketplace.domain.discount.usecase.DeleteDiscountUseCase;
import com.marketplace.domain.discount.usecase.GetDiscountsByShopUseCase;
import com.marketplace.domain.discount.usecase.SaveDiscountUseCase;

@Component
public class DiscountControllerFacade {

	@Autowired
	private SaveDiscountUseCase saveDiscountUseCase;
	
	@Autowired
	private DeleteDiscountUseCase deleteDiscountUseCase;
	
	@Autowired
	private ApplyDiscountsUseCase applyDiscountsUseCase;
	
	@Autowired
	private GetDiscountsByShopUseCase getDiscountsByShopUseCase;
	
	@Autowired
	private VendorDataMapper mapper;
	
    public void save(long shopId, DiscountEditDTO values) {
    	values.setShopId(shopId);
        saveDiscountUseCase.apply(mapper.map(values));
    }

    public void delete(long id) {
        deleteDiscountUseCase.apply(id);
    }

    public void applyDiscounts(long shopId, long discountId, List<Long> productIds) {
        applyDiscountsUseCase.apply(shopId, discountId, productIds);
    }
	
    public List<DiscountDTO> findByShop(long shopId) {
    	var source = getDiscountsByShopUseCase.apply(shopId);
        return mapper.mapDiscountList(source);
    }
}
