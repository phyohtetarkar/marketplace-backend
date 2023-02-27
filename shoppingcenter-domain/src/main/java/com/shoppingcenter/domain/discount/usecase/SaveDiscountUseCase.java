package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.discount.Discount;

public interface SaveDiscountUseCase {

    void apply(Discount discount);

}
