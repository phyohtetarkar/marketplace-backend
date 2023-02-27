package com.shoppingcenter.domain.discount.usecase;

import java.util.List;

public interface ApplyDiscountsUseCase {

    void applyDiscounts(long discountId, List<Long> productIds);

}
