package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemEditDTO;
import com.shoppingcenter.core.shoppingcart.CartItemService;
import com.shoppingcenter.core.shoppingcart.model.CartItem;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/cart-items")
@Tag(name = "ShoppingCart")
public class ShoppingCartController {

    @Autowired
    private CartItemService service;

    @Autowired
    private ModelMapper modelmapper;

    @PostMapping
    public void addToCart(@RequestBody CartItemEditDTO item, Authentication authentication) {
        item.setUserId(authentication.getName());
        service.addToCart(modelmapper.map(item, CartItem.class));
    }

    @PutMapping
    public void updateQuantity(@RequestBody CartItemEditDTO item, Authentication authentication) {
        item.setUserId(authentication.getName());
        service.updateQuantity(modelmapper.map(item, CartItem.class));
    }

    @DeleteMapping
    public void removeFromCart(@RequestBody List<CartItem.ID> ids, Authentication authentication) {
        service.removeAll(authentication.getName(), ids);
    }

    @GetMapping
    public List<CartItemDTO> findAll(Authentication authentication) {
        return modelmapper.map(service.findByUser(authentication.getName()), CartItemDTO.listType());
    }
}
