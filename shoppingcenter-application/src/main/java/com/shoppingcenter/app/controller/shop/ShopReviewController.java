package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopReviewDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopReviewEditDTO;
import com.shoppingcenter.domain.SortQuery.Direction;
import com.shoppingcenter.domain.common.AuthenticationContext;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-reviews")
@Tag(name = "ShopReview")
public class ShopReviewController {

    @Autowired
    private ShopReviewFacade shopReviewFacade;

    @Autowired
    private AuthenticationContext authentication;

    @PostMapping
    public void writeReview(@PathVariable long shopId, @RequestBody ShopReviewEditDTO review) {
        review.setUserId(authentication.getUserId());
        shopReviewFacade.writeReview(review);
    }

    @GetMapping("{shopId:\\d+}/me")
    public ShopReviewDTO findUserReview(@PathVariable long shopId) {
        var userId = authentication.getUserId();
        var review = shopReviewFacade.findUserReview(shopId, userId);
        return review;
    }

    @GetMapping
    public PageDataDTO<ShopReviewDTO> findAll(
            @PathVariable long shopId,
            @RequestParam Direction direction,
            @RequestParam(required = false) Integer page) {
        return shopReviewFacade.findReviewsByShop(shopId, direction, page);
    }
}
