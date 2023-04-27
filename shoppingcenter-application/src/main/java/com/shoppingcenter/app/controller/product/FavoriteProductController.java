package com.shoppingcenter.app.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.domain.common.AuthenticationContext;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/favorite-products")
@Tag(name = "FavoriteProduct")
public class FavoriteProductController {

    @Autowired
    private FavoriteProductFacade favoriteProductFacade;

    @Autowired
    private AuthenticationContext authentication;

    @PostMapping("{productId:\\d+}")
    public void addToFavorite(@PathVariable long productId) {
        favoriteProductFacade.add(authentication.getUserId(), productId);
    }

    @GetMapping("{productId:\\d+}/check")
    public boolean checkFavorite(@PathVariable long productId) {
        return favoriteProductFacade.checkFavorite(authentication.getUserId(), productId);
    }

    @DeleteMapping("{productId:\\d+}")
    public void removeFromFavorite(@PathVariable long productId) {
        favoriteProductFacade.remove(authentication.getUserId(), productId);
    }

}
