package com.shoppingcenter.domain.shoppingcart.usecase;

public interface UpdateCartItemQuantityUseCase {

    void apply(long itemId, int quantity);

}
