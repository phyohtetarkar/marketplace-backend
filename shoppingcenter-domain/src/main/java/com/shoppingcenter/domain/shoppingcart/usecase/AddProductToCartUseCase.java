package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.shoppingcart.CartItem;

public interface AddProductToCartUseCase {
    boolean apply(CartItem item);
}
