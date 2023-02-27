package com.shoppingcenter.app.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.review.dto.ShopReviewDTO;
import com.shoppingcenter.app.controller.review.dto.ShopReviewEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.SortQuery.Direction;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-reviews")
@Tag(name = "ShopReview")
public class ShopReviewController {
    @Autowired
    private ShopReviewFacade shopReviewFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void writeReview(
            @RequestBody ShopReviewEditDTO review,
            Authentication authentication) {
        review.setUserId(authentication.getName());
        shopReviewFacade.writeReview(review);
    }

    @PutMapping
    public void updateReview(
            @RequestBody ShopReviewEditDTO review,
            Authentication authentication) {
        review.setUserId(authentication.getName());
        shopReviewFacade.updateReview(review);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteReview(@PathVariable long id, Authentication authentication) {
        shopReviewFacade.delete(authentication.getName(), id);
    }

    @GetMapping("{shopId:\\d+}/me")
    public ShopReviewDTO findUserReview(@PathVariable long shopId, Authentication authentication) {
        String userId = authentication.getName();
        var review = shopReviewFacade.findUserReview(shopId, userId);
        return review;
    }

    @GetMapping("{shopId:\\d+}")
    public PageData<ShopReviewDTO> findAll(
            @PathVariable long shopId,
            @RequestParam Direction direction,
            @RequestParam(required = false) Integer page) {
        return shopReviewFacade.findReviewsByShop(shopId, direction, page);
    }
}
