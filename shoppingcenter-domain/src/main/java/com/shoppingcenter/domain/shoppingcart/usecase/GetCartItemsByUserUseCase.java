package com.shoppingcenter.domain.shoppingcart.usecase;

import java.util.List;

import com.shoppingcenter.domain.shoppingcart.CartItem;

public interface GetCartItemsByUserUseCase {

    List<CartItem> apply(long userId);

}
