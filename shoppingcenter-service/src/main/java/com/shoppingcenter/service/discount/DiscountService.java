package com.shoppingcenter.service.discount;

import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.discount.model.Discount;

public interface DiscountService {

    void save(Discount discount);

    void delete(long id);

    Discount findById(long id);

    PageData<Discount> findAll(long shopId, Integer page);
}
