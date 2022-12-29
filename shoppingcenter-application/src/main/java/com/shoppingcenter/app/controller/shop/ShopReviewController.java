package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/v1/shops/{id:\\d+}")
@Tag(name = "ShopReview")
public class ShopReviewController {

    @Autowired
    private ShopReviewService service;

    @PostMapping("reviews")
    public void writeReview(@PathVariable long id, @RequestBody ShopReview review) {
        service.writeReview(review);
    }

    @PreAuthorize("#userId == authentication.name")
    @DeleteMapping("reviews/{reviewId:\\d+}")
    public void deleteReview(
            @PathVariable long id,
            @PathVariable long reviewId,
            @RequestParam("user-id") String userId) {
        service.deleteReview(id, userId);
    }

    @GetMapping("reviews")
    public PageData<ShopReview> findAll(@PathVariable long id, @RequestParam(required = false) Integer page) {
        return service.findReviewsByShop(id, page);
    }
}
