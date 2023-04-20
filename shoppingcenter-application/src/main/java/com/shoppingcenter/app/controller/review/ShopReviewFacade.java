package com.shoppingcenter.app.controller.review;

import org.hibernate.StaleObjectStateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.review.dto.ShopReviewDTO;
import com.shoppingcenter.app.controller.review.dto.ShopReviewEditDTO;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.SortQuery.Direction;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.usecase.GetAllShopReviewUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopReviewByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.WriteShopReviewUseCase;

@Facade
public class ShopReviewFacade {

    @Autowired
    private WriteShopReviewUseCase writeShopReviewUseCase;

    @Autowired
    private GetShopReviewByUserUseCase getShopReviewByUserUseCase;

    @Autowired
    private GetAllShopReviewUseCase getAllShopReviewUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Retryable(retryFor = { StaleObjectStateException.class })
    @Transactional
    public void writeReview(ShopReviewEditDTO review) {
        writeShopReviewUseCase.apply(modelMapper.map(review, ShopReview.class));
    }

    @Retryable(retryFor = { StaleObjectStateException.class })
    @Transactional
    public void updateReview(ShopReviewEditDTO review) {
        writeShopReviewUseCase.apply(modelMapper.map(review, ShopReview.class));
    }

    public void delete(ShopReviewDTO review) {

    }

    public ShopReviewDTO findUserReview(long shopId, long userId) {
        var source = getShopReviewByUserUseCase.apply(shopId, userId);
        return source != null ? modelMapper.map(source, ShopReviewDTO.class) : null;
    }

    public PageDataDTO<ShopReviewDTO> findReviewsByShop(long shopId, Direction direction, Integer page) {
        return modelMapper.map(getAllShopReviewUseCase.apply(shopId, page, SortQuery.of(direction, "modifiedAt")),
                ShopReviewDTO.pagType());
    }

}
