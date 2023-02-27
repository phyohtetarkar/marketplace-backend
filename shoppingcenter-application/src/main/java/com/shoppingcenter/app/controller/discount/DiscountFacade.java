package com.shoppingcenter.app.controller.discount;

import java.util.List;

import com.shoppingcenter.app.controller.discount.dto.DiscountDTO;
import com.shoppingcenter.app.controller.discount.dto.DiscountEditDTO;
import com.shoppingcenter.domain.PageData;

public interface DiscountFacade {

    void save(DiscountEditDTO discount);

    void delete(long id);

    void applyDiscounts(long discountId, List<Long> productIds);

    void removeDiscount(long discountId, Long productId);

    DiscountDTO findById(long id);

    PageData<DiscountDTO> findByShop(long shopId, Integer page);

}
