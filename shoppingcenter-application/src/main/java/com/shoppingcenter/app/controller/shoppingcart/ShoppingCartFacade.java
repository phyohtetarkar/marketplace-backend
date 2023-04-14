package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemEditDTO;
import com.shoppingcenter.domain.shoppingcart.CartItem;

public interface ShoppingCartFacade {

    void addToCart(CartItemEditDTO item);

    CartItemDTO updateQuantity(CartItemEditDTO item);

    void removeFromCart(List<CartItem> items, long userId);

    void removeByUser(long userId);

    long countByUser(long userId);

    List<CartItemDTO> findByUser(long userId);

}
