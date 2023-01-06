package com.shoppingcenter.core.discount;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.discount.model.Discount;

public interface DiscountService {

    void save(Discount discount);

    void delete(long id);

    Discount findById(long id);

    PageData<Discount> findAll(long shopId, Integer page);
}
