package com.marketplace.api.consumer.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.review.ShopReviewInput;
import com.marketplace.domain.review.usecase.GetAllShopReviewUseCase;
import com.marketplace.domain.review.usecase.GetShopReviewByUserUseCase;
import com.marketplace.domain.review.usecase.WriteShopReviewUseCase;

@Component
public class ShopReviewControllerFacade extends AbstractControllerFacade {

	@Autowired
	private WriteShopReviewUseCase writeShopReviewUseCase;

	@Autowired
	private GetShopReviewByUserUseCase getShopReviewByUserUseCase;

	@Autowired
	private GetAllShopReviewUseCase getAllShopReviewUseCase;

	public void writeReview(ShopReviewEditDTO review) {
		writeShopReviewUseCase.apply(map(review, ShopReviewInput.class));
	}

	public ShopReviewDTO findUserReview(long shopId, long userId) {
		var source = getShopReviewByUserUseCase.apply(shopId, userId);
		return map(source, ShopReviewDTO.class);
	}

	public PageDataDTO<ShopReviewDTO> findReviewsByShop(long shopId, Integer page) {
		var source = getAllShopReviewUseCase.apply(shopId, page);
		return map(source, ShopReviewDTO.pagType());
	}
}
