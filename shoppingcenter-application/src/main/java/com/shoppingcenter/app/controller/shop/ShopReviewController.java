package com.shoppingcenter.app.controller.shop;

import org.modelmapper.ModelMapper;
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

import com.shoppingcenter.app.controller.shop.dto.ShopReviewDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopReviewEditDTO;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.shop.ShopReviewService;
import com.shoppingcenter.core.shop.model.ShopReview;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-reviews")
@Tag(name = "ShopReview")
public class ShopReviewController {

    @Autowired
    private ShopReviewService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public void writeReview(
            @RequestBody ShopReviewEditDTO review,
            Authentication authentication) {
        review.setUserId(authentication.getName());
        service.writeReview(modelMapper.map(review, ShopReview.class));
    }

    @DeleteMapping("{shopId:\\d+}")
    public void deleteReview(
            @PathVariable long shopId,
            Authentication authentication) {
        service.deleteReview(shopId, authentication.getName());
    }

    @GetMapping("{shopId:\\d+}")
    public PageData<ShopReviewDTO> findAll(
            @PathVariable long shopId,
            @RequestParam Direction direction,
            @RequestParam(required = false) Integer page) {
        return modelMapper.map(service.findReviewsByShop(shopId, direction, page), ShopReviewDTO.pagType());
    }
}
