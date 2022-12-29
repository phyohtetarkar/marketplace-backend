package com.shoppingcenter.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.product.FavoriteProductService;
import com.shoppingcenter.core.product.model.Product;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/users/{id}")
@Tag(name = "UserFavorite")
public class UserFavoriteController {

    @Autowired
    private FavoriteProductService favoriteProductService;

    @PreAuthorize("#id == authentication.name")
    @GetMapping("favorite-products")
    public PageData<Product> getFavoriteProducts(
            @PathVariable String id,
            @RequestParam(required = false) Integer page) {
        return favoriteProductService.findByUser(id, page);
    }

    @PreAuthorize("#id == authentication.name")
    @PostMapping("favorite-products")
    public void addToFavorite(
            @PathVariable String id,
            @RequestParam("product-id") long productId) {
        favoriteProductService.add(id, productId);
    }

    @PreAuthorize("#id == authentication.name")
    @DeleteMapping("favorite-products")
    public void removeFromFavorite(
            @PathVariable String id,
            @RequestParam("product-id") long productId) {
        favoriteProductService.remove(id, productId);
    }
}
