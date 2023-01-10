package com.shoppingcenter.core.discount;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.discount.model.Discount;

public interface DiscountService {

    void save(Discount discount);

    void delete(Discount.ID id);

    Discount findById(Discount.ID id);

    PageData<Discount> findAll(long shopId, Integer page);
}
