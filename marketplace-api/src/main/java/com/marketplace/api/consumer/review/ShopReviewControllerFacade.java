package com.marketplace.api.consumer.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.domain.review.usecase.GetAllShopReviewUseCase;
import com.marketplace.domain.review.usecase.GetShopReviewByUserUseCase;
import com.marketplace.domain.review.usecase.WriteShopReviewUseCase;

@Component
public class ShopReviewControllerFacade {

	@Autowired
	private WriteShopReviewUseCase writeShopReviewUseCase;

	@Autowired
	private GetShopReviewByUserUseCase getShopReviewByUserUseCase;

	@Autowired
	private GetAllShopReviewUseCase getAllShopReviewUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;

	public void writeReview(ShopReviewEditDTO review) {
		writeShopReviewUseCase.apply(mapper.map(review));
	}

	public ShopReviewDTO findUserReview(long shopId, long userId) {
		var source = getShopReviewByUserUseCase.apply(shopId, userId);
		return mapper.map(source);
	}

	public PageDataDTO<ShopReviewDTO> findReviewsByShop(long shopId, Integer page) {
		var source = getAllShopReviewUseCase.apply(shopId, page);
		return mapper.mapShopReviewPage(source);
	}
}
