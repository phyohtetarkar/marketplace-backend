package com.marketplace.api.consumer.shoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.domain.shoppingcart.CartItem;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/cart-items")
@Tag(name = "Consumer")
public class CartItemController {

	@Autowired
	private CartItemControllerFacade cartItemFacade;
	
	@PostMapping
    public void addToCart(@RequestBody CartItemEditDTO body) {
        body.setUserId(AuthenticationUtil.getAuthenticatedUserId());
        cartItemFacade.addToCart(body);
    }

    @PutMapping
    public void update(@RequestBody CartItemEditDTO body) {
    	 body.setUserId(AuthenticationUtil.getAuthenticatedUserId());
    	 cartItemFacade.updateQuantity(body);
    }

    @DeleteMapping
    public void removeFromCart(@RequestBody List<CartItem.ID> items) {
    	cartItemFacade.removeFromCart(items);
    }
}
