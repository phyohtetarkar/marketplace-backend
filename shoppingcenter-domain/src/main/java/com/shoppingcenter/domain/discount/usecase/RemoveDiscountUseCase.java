package com.shoppingcenter.domain.discount.usecase;

public interface RemoveDiscountUseCase {

    void apply(long discountId, Long productId);

}
