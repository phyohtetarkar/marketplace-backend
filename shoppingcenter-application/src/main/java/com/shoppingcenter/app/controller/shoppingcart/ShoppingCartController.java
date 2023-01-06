package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.core.shoppingcart.CartItemService;
import com.shoppingcenter.core.shoppingcart.model.CartItem;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/cart-items")
@Tag(name = "ShoppingCart")
public class ShoppingCartController {

    @Autowired
    private CartItemService service;

    @PostMapping
    public void addToCart(@RequestBody CartItem item, Authentication authentication) {
        item.getId().setUserId(authentication.getName());
        service.addToCart(item);
    }

    @PutMapping
    public void updateQuantity(@RequestBody CartItem item, Authentication authentication) {
        item.getId().setUserId(authentication.getName());
        service.updateQuantity(item);
    }

    @DeleteMapping
    public void removeFromCart(@RequestBody List<CartItem.Id> ids, Authentication authentication) {
        service.removeAll(authentication.getName(), ids);
    }

    @GetMapping
    public List<CartItem> findAll(Authentication authentication) {
        return service.findByUser(authentication.getName());
    }
}
