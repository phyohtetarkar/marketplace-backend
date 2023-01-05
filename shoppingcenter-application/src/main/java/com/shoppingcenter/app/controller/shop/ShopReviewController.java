package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.shop.ShopReviewService;
import com.shoppingcenter.core.shop.model.ShopReview;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops/{id:\\d+}/reviews")
@Tag(name = "ShopReview")
public class ShopReviewController {

    @Autowired
    private ShopReviewService service;

    @PostMapping
    public void writeReview(
            @PathVariable long id,
            @RequestBody ShopReview review,
            Authentication authentication) {
        review.setUserId(authentication.getName());
        service.writeReview(review);
    }

    @DeleteMapping
    public void deleteReview(
            @PathVariable long id,
            Authentication authentication) {
        service.deleteReview(id, authentication.getName());
    }

    @GetMapping
    public PageData<ShopReview> findAll(
            @PathVariable long id,
            @RequestParam Direction direction,
            @RequestParam(required = false) Integer page) {
        return service.findReviewsByShop(id, direction, page);
    }
}
