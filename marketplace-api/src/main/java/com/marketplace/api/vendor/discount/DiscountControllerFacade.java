package com.marketplace.api.vendor.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.domain.discount.DiscountInput;
import com.marketplace.domain.discount.usecase.ApplyDiscountsUseCase;
import com.marketplace.domain.discount.usecase.DeleteDiscountUseCase;
import com.marketplace.domain.discount.usecase.GetDiscountsByShopUseCase;
import com.marketplace.domain.discount.usecase.SaveDiscountUseCase;

@Component
public class DiscountControllerFacade extends AbstractControllerFacade {

	@Autowired
	private SaveDiscountUseCase saveDiscountUseCase;
	
	@Autowired
	private DeleteDiscountUseCase deleteDiscountUseCase;
	
	@Autowired
	private ApplyDiscountsUseCase applyDiscountsUseCase;
	
	@Autowired
	private GetDiscountsByShopUseCase getDiscountsByShopUseCase;
	
    public void save(long shopId, DiscountEditDTO values) {
    	values.setShopId(shopId);
        saveDiscountUseCase.apply(map(values, DiscountInput.class));
    }

    public void delete(long id) {
        deleteDiscountUseCase.apply(id);
    }

    public void applyDiscounts(long shopId, long discountId, List<Long> productIds) {
        applyDiscountsUseCase.apply(shopId, discountId, productIds);
    }
	
    public List<DiscountDTO> findByShop(long shopId) {
        return map(getDiscountsByShopUseCase.apply(shopId), DiscountDTO.listType());
    }
}
