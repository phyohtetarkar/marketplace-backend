package com.shoppingcenter.app.controller.discount;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.discount.dto.DiscountDTO;
import com.shoppingcenter.app.controller.discount.dto.DiscountEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.domain.discount.usecase.ApplyDiscountsUseCase;
import com.shoppingcenter.domain.discount.usecase.DeleteDiscountUseCase;
import com.shoppingcenter.domain.discount.usecase.GetDiscountsByShopUseCase;
import com.shoppingcenter.domain.discount.usecase.RemoveDiscountUseCase;
import com.shoppingcenter.domain.discount.usecase.SaveDiscountUseCase;

@Facade
public class DiscountFacadeImpl implements DiscountFacade {

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

    @Override
    public void save(DiscountEditDTO discount) {
        saveDiscountUseCase.apply(modelMapper.map(discount, Discount.class));
    }

    @Override
    public void delete(long id) {
        deleteDiscountUseCase.apply(id);
    }

    @Override
    public void applyDiscounts(long discountId, List<Long> productIds) {
        applyDiscountsUseCase.applyDiscounts(discountId, productIds);
    }

    @Override
    public void removeDiscount(long discountId, Long productId) {
        removeDiscountUseCase.apply(discountId, productId);
    }

    @Override
    public DiscountDTO findById(long id) {
        // TODO: implementation
        return null;
    }

    @Override
    public PageData<DiscountDTO> findByShop(long shopId, Integer page) {
        return modelMapper.map(getDiscountsByShopUseCase.apply(shopId, page), DiscountDTO.pageType());
    }

}
