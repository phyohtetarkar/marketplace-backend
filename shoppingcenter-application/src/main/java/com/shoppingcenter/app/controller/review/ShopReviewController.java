package com.shoppingcenter.app.controller.review;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
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
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.shop.ShopReviewService;
import com.shoppingcenter.service.shop.model.ShopReview;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-reviews")
@Tag(name = "ShopReview")
public class ShopReviewController {

    @Autowired
    private ShopReviewService service;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void writeReview(
            @RequestBody ShopReviewEditDTO review,
            Authentication authentication) {
        review.setUserId(authentication.getName());
        service.writeReview(modelMapper.map(review, ShopReview.class));
    }

    @PutMapping
    public void updateReview(
            @RequestBody ShopReviewEditDTO review,
            Authentication authentication) {
        review.setUserId(authentication.getName());
        service.updateReview(modelMapper.map(review, ShopReview.class));
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteReview(@PathVariable long id, Authentication authentication) {
        service.delete(id);
    }

    @GetMapping("{shopId:\\d+}/me")
    public ShopReviewDTO findUserReview(@PathVariable long shopId, Authentication authentication) {
        String userId = authentication.getName();
        ShopReview review = service.findUserReview(shopId, userId);
        if (review != null) {
            return modelMapper.map(review, ShopReviewDTO.class);
        }
        return null;
    }

    @GetMapping("{shopId:\\d+}")
    public PageData<ShopReviewDTO> findAll(
            @PathVariable long shopId,
            @RequestParam Direction direction,
            @RequestParam(required = false) Integer page) {
        return modelMapper.map(service.findReviewsByShop(shopId, direction, page), ShopReviewDTO.pagType());
    }
}
