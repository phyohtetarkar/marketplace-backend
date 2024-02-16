package com.marketplace.api.consumer.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.api.PageDataDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/shops/{shopId:\\d+}/reviews")
@Tag(name = "Consumer")
public class ShopReviewController {

	@Autowired
	private ShopReviewControllerFacade shopReviewFacade;
	
	@PostMapping
    public void writeReview(
    		@PathVariable long shopId, 
    		@RequestBody ShopReviewEditDTO review) {
        review.setUserId(AuthenticationUtil.getAuthenticatedUserId());
        review.setShopId(shopId);
        shopReviewFacade.writeReview(review);
    }

    @GetMapping("{userId:\\d+}")
    public ShopReviewDTO findUserReview(
    		@PathVariable long shopId,
    		@PathVariable long userId) {
        return shopReviewFacade.findUserReview(shopId, userId);
    }

    @GetMapping
    public PageDataDTO<ShopReviewDTO> findAll(
    		@PathVariable long shopId,
            @RequestParam(required = false) Integer page) {
        return shopReviewFacade.findReviewsByShop(shopId, page);
    }
}
