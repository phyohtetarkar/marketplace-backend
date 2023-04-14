package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.shoppingcart.CartItem;

public interface UpdateCartItemQuantityUseCase {

    void apply(CartItem item);

}
