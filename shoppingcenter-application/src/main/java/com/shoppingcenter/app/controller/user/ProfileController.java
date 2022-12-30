package com.shoppingcenter.app.controller.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.product.FavoriteProductService;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.core.shop.ShopQueryService;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.core.user.UserService;
import com.shoppingcenter.core.user.model.User;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/profile")
@Tag(name = "Profile")
public class ProfileController {

    @Autowired
    private UserService service;

    @Autowired
    private ShopQueryService shopQueryService;

    @Autowired
    private FavoriteProductService favoriteProductService;

    @PutMapping
    public void update(@RequestBody User user, Authentication authentication) {
        user.setId(authentication.getName());
        service.update(user);
    }

    @PostMapping(value = "image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadImage(@RequestPart MultipartFile file, Authentication authentication) {
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setFile(file.getResource().getFile());
            uploadFile.setOriginalFileName(file.getOriginalFilename());
            uploadFile.setSize(file.getSize());
            service.uploadImage(authentication.getName(), uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("shops")
    public PageData<Shop> getShops(
            @RequestParam(required = false) Integer page,
            Authentication authentication) {
        return shopQueryService.findByUser(authentication.getName(), page);
    }

    @GetMapping("favorite-products")
    public PageData<Product> getFavoriteProducts(
            @RequestParam(required = false) Integer page,
            Authentication authentication) {
        return favoriteProductService.findByUser(authentication.getName(), page);
    }

    @PostMapping("favorite-products")
    public void addToFavorite(
            @RequestParam("product-id") long productId,
            Authentication authentication) {
        favoriteProductService.add(authentication.getName(), productId);
    }

    @DeleteMapping("favorite-products")
    public void removeFromFavorite(
            @RequestParam("product-id") long productId,
            Authentication authentication) {
        favoriteProductService.remove(authentication.getName(), productId);
    }

}
