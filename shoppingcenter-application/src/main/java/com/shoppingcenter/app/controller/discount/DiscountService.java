package com.shoppingcenter.app.controller.discount;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.discount.dto.DiscountDTO;
import com.shoppingcenter.app.controller.discount.dto.DiscountEditDTO;
import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.domain.discount.DiscountDao;
import com.shoppingcenter.domain.discount.usecase.ApplyDiscountsUseCase;
import com.shoppingcenter.domain.discount.usecase.DeleteDiscountUseCase;
import com.shoppingcenter.domain.discount.usecase.GetDiscountsByShopUseCase;
import com.shoppingcenter.domain.discount.usecase.RemoveDiscountUseCase;
import com.shoppingcenter.domain.discount.usecase.SaveDiscountUseCase;

@Service
public class DiscountService {
	
	@Autowired
	private DiscountDao discountDao;

    @Autowired
    private SaveDiscountUseCase saveDiscountUseCase;

    @Autowired
    private DeleteDiscountUseCase deleteDiscountUseCase;

    @Autowired
    private ApplyDiscountsUseCase applyDiscountsUseCase;

    @Autowired
    private RemoveDiscountUseCase removeDiscountUseCase;

    @Autowired
    private GetDiscountsByShopUseCase getDiscountsByShopUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void save(DiscountEditDTO discount) {
        saveDiscountUseCase.apply(modelMapper.map(discount, Discount.class));
    }

    @Transactional
    public void delete(long id) {
        deleteDiscountUseCase.apply(id);
    }

    @Transactional
    public void applyDiscounts(long discountId, List<Long> productIds) {
        applyDiscountsUseCase.apply(discountId, productIds);
    }

    @Transactional
    public void removeDiscount(long discountId, Long productId) {
        removeDiscountUseCase.apply(discountId, productId);
    }

    public DiscountDTO findById(long id) {
        return null;
    }
    
    public List<DiscountDTO> findByShop(long shopId) {
    	return modelMapper.map(discountDao.findById(shopId), DiscountDTO.listType());
    }

    public PageDataDTO<DiscountDTO> findByShop(long shopId, Integer page) {
        return modelMapper.map(getDiscountsByShopUseCase.apply(shopId, page), DiscountDTO.pageType());
    }

}
