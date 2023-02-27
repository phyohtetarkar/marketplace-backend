package com.shoppingcenter.app.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.domain.PageData;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/favorite-products")
@Tag(name = "FavoriteProduct")
public class FavoriteProductController {

    @Autowired
    private FavoriteProductFacade favoriteProductFacade;

    @PostMapping
    public void addToFavorite(@RequestParam("product-id") long productId, Authentication authentication) {
        favoriteProductFacade.add(authentication.getName(), productId);
    }

    @GetMapping("check")
    public boolean checkFavorite(@RequestParam("product-id") long productId, Authentication authentication) {
        return favoriteProductFacade.checkFavorite(authentication.getName(), productId);
    }

    @DeleteMapping
    public void removeFromFavorite(@RequestParam("product-id") long productId, Authentication authentication) {
        favoriteProductFacade.remove(productId);
    }

    @GetMapping
    public PageData<ProductDTO> getFavoriteProducts(
            @RequestParam(required = false) Integer page,
            Authentication authentication) {
        return favoriteProductFacade.findByUser(authentication.getName(), page);
    }

}
