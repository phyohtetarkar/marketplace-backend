package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemEditDTO;
import com.shoppingcenter.domain.shoppingcart.usecase.AddProductToCartUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.CountCartItemByUserUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.GetCartItemsByUserUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.RemoveProductFromCartUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.UpdateCartItemQuantityUseCase;

@Facade
@Transactional
public class ShoppingCartFacadeImpl implements ShoppingCartFacade {

    @Autowired
    private AddProductToCartUseCase addProductToCartUseCase;

    @Autowired
    private UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase;

    @Autowired
    private RemoveProductFromCartUseCase removeProductFromCartUseCase;

    @Autowired
    private CountCartItemByUserUseCase countCartItemByUserUseCase;

    @Autowired
    private GetCartItemsByUserUseCase getCartItemsByUserUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addToCart(CartItemEditDTO item) {
        // addProductToCartUseCase.apply(modelMapper.map(item, CartItem.class));
    }

    @Override
    public CartItemDTO updateQuantity(CartItemEditDTO item) {
        // updateCartItemQuantityUseCase.apply(modelMapper.map(item, CartItem.class));

        return null;
    }

    @Override
    public void removeFromCart(List<CartItemDTO> items, long userId) {
        // removeProductFromCartUseCase.apply(items.stream().map(itm -> {
        // itm.setUserId(userId);
        // return itm;
        // }).toList());
    }

    @Override
    public void removeByUser(long userId) {

    }

    @Override
    public long countByUser(long userId) {
        return countCartItemByUserUseCase.apply(userId);
    }

    @Override
    public List<CartItemDTO> findByUser(long userId) {
        return modelMapper.map(getCartItemsByUserUseCase.apply(userId), CartItemDTO.listType());
    }

}
