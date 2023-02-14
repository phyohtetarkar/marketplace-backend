package com.shoppingcenter.service.discount;

import java.util.List;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.discount.model.Discount;

public interface DiscountService {

    void save(Discount discount);

    void delete(long id);

    void applyDiscounts(long discountId, List<Long> productIds);

    void applyDiscountAll(long discountId);

    void removeDiscount(long discountId, long productId);

    void removeDiscountAll(long discountId);

    Discount findById(long id);

    PageData<Discount> findAll(long shopId, Integer page);
}
