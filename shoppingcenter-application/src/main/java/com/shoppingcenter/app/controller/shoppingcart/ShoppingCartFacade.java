package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.AddToCartDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.UpdateCartItemDTO;
import com.shoppingcenter.domain.shoppingcart.AddToCartInput;
import com.shoppingcenter.domain.shoppingcart.usecase.AddProductToCartUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.CountCartItemByUserUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.GetCartItemsByUserUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.RemoveProductFromCartUseCase;
import com.shoppingcenter.domain.shoppingcart.usecase.UpdateCartItemQuantityUseCase;

@Facade
@Transactional
public class ShoppingCartFacade {

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

	public void addToCart(AddToCartDTO item) {
		addProductToCartUseCase.apply(modelMapper.map(item, AddToCartInput.class));
	}

	public void updateQuantity(UpdateCartItemDTO item) {
		updateCartItemQuantityUseCase.apply(item.getId(), item.getQuantity());
	}

	public void removeFromCart(List<Long> items, long userId) {
		removeProductFromCartUseCase.apply(items);
	}

	public void removeByUser(long userId) {

	}

	public long countByUser(long userId) {
		return countCartItemByUserUseCase.apply(userId);
	}

	public List<CartItemDTO> findByUser(long userId) {
		return modelMapper.map(getCartItemsByUserUseCase.apply(userId), CartItemDTO.listType());
	}

}
