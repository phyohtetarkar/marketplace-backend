package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemEditDTO;
import com.shoppingcenter.domain.shoppingcart.CartItem;
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
        addProductToCartUseCase.apply(modelMapper.map(item, CartItem.class));
    }

    @Override
    public CartItemDTO updateQuantity(long id, int quantity) {
        updateCartItemQuantityUseCase.apply(id, quantity);

        return null;
    }

    @Override
    public void removeFromCart(List<Long> idList) {
        removeProductFromCartUseCase.apply(idList);
    }

    @Override
    public void removeByUser(String userId) {
        // TODO Auto-generated method stub
    }

    @Override
    public long countByUser(String userId) {
        return countCartItemByUserUseCase.apply(userId);
    }

    @Override
    public List<CartItemDTO> findByUser(String userId) {
        return modelMapper.map(getCartItemsByUserUseCase.apply(userId), CartItemDTO.listType());
    }

}
