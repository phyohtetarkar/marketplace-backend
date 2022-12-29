package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PreAuthorize("#item.id.userId == authentication.name")
    @PostMapping
    public void addToCart(@RequestBody CartItem item) {
        service.addToCart(item);
    }

    @DeleteMapping
    public void removeFromCart(@RequestBody List<CartItem.Id> ids) {
        service.removeAll(ids);
    }

    @GetMapping
    public List<CartItem> findAll(@RequestParam("user-id") String userId) {
        return service.findByUser(userId);
    }
}
