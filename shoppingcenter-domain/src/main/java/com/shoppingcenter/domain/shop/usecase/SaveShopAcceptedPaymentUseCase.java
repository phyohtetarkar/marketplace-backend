package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.shop.ShopAcceptedPayment;

public interface SaveShopAcceptedPaymentUseCase {

    void apply(ShopAcceptedPayment payment);

}
