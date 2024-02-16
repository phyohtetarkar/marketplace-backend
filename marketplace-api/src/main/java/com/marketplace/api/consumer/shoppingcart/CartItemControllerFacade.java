package com.marketplace.api.consumer.shoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.AuthenticationUtil;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemInput;
import com.marketplace.domain.shoppingcart.usecase.AddProductToCartUseCase;
import com.marketplace.domain.shoppingcart.usecase.RemoveProductFromCartUseCase;
import com.marketplace.domain.shoppingcart.usecase.UpdateCartItemUseCase;

@Component
public class CartItemControllerFacade extends AbstractControllerFacade {

	
	@Autowired
	private AddProductToCartUseCase addProductToCartUseCase;

	@Autowired
	private UpdateCartItemUseCase updateCartItemQuantityUseCase;

	@Autowired
	private RemoveProductFromCartUseCase removeProductFromCartUseCase;
	
	public void addToCart(CartItemEditDTO values) {
		addProductToCartUseCase.apply(modelMapper.map(values, CartItemInput.class));
	}

	public void updateQuantity(CartItemEditDTO values) {
		updateCartItemQuantityUseCase.apply(modelMapper.map(values, CartItemInput.class));
	}

	public void removeFromCart(List<CartItem.ID> values) {
		for (CartItem.ID id : values) {
			id.setUserId(AuthenticationUtil.getAuthenticatedUserId());
		}
		removeProductFromCartUseCase.apply(values);
	}
}
