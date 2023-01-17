package com.shoppingcenter.core.discount;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.discount.model.Discount;

public interface DiscountService {

    void save(Discount discount);

    void delete(String id);

    Discount findById(String id);

    PageData<Discount> findAll(long shopId, Integer page);
}
