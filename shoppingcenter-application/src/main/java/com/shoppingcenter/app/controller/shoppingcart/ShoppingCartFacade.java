package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemEditDTO;

public interface ShoppingCartFacade {

    void addToCart(CartItemEditDTO item);

    CartItemDTO updateQuantity(long id, int quantity);

    void removeFromCart(List<Long> idList);

    void removeByUser(String userId);

    long countByUser(String userId);

    List<CartItemDTO> findByUser(String userId);

}
