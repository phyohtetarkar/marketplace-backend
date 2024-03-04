package com.marketplace.api.consumer.shoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.usecase.AddProductToCartUseCase;
import com.marketplace.domain.shoppingcart.usecase.RemoveProductFromCartUseCase;
import com.marketplace.domain.shoppingcart.usecase.UpdateCartItemUseCase;

@Component
public class CartItemControllerFacade {
	
	@Autowired
	private AddProductToCartUseCase addProductToCartUseCase;

	@Autowired
	private UpdateCartItemUseCase updateCartItemQuantityUseCase;

	@Autowired
	private RemoveProductFromCartUseCase removeProductFromCartUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;
	
	public void addToCart(CartItemEditDTO values) {
		addProductToCartUseCase.apply(mapper.map(values));
	}

	public void updateQuantity(CartItemEditDTO values) {
		updateCartItemQuantityUseCase.apply(mapper.map(values));
	}

	public void removeFromCart(List<CartItem.ID> values) {
		for (CartItem.ID id : values) {
			id.setUserId(AuthenticationUtil.getAuthenticatedUserId());
		}
		removeProductFromCartUseCase.apply(values);
	}
}
