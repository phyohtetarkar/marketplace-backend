package com.shoppingcenter.app.controller.shoppingcart;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemEditDTO;
import com.shoppingcenter.service.shoppingcart.CartItemService;
import com.shoppingcenter.service.shoppingcart.model.CartItem;

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

    @PutMapping("{id:\\d+}")
    public CartItemDTO updateQuantity(@PathVariable long id, @RequestParam int quantity,
            Authentication authentication) {
        String userId = authentication.getName();
        CartItem item = service.updateQuantity(id, quantity, userId);
        return modelmapper.map(item, CartItemDTO.class);
    }

    @DeleteMapping
    public void removeFromCart(@RequestBody List<Long> ids, Authentication authentication) {
        service.removeAll(authentication.getName(), ids);
    }

    @GetMapping("count")
    public long getItemCount(Authentication authentication) {
        return service.countByUser(authentication.getName());
    }

    @GetMapping
    public List<CartItemDTO> findAll(Authentication authentication) {
        return modelmapper.map(service.findByUser(authentication.getName()), CartItemDTO.listType());
    }
}
