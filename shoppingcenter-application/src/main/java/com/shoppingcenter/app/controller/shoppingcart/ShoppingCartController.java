package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemEditDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.UpdateCartItemDTO;
import com.shoppingcenter.domain.common.AuthenticationContext;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/cart-items")
@Tag(name = "ShoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartFacade shoppingCartFacade;

    @Autowired
    private AuthenticationContext authentication;

    @PostMapping
    public void addToCart(@RequestBody CartItemEditDTO item) {
        item.setUserId(authentication.getUserId());
        shoppingCartFacade.addToCart(item);
    }

    @PutMapping
    public void updateQuantity(@RequestBody UpdateCartItemDTO item) {
        item.setUserId(authentication.getUserId());
        shoppingCartFacade.updateQuantity(item);
    }

    @DeleteMapping
    public void removeFromCart(@RequestBody List<Long> items) {
        shoppingCartFacade.removeFromCart(items, authentication.getUserId());
    }

    // @GetMapping
    // public List<CartItemDTO> findAll() {
    // return shoppingCartFacade.findByUser(authentication.getUserId());
    // }
}
