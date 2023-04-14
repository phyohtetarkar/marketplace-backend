package com.shoppingcenter.app.controller.review;

import org.hibernate.StaleObjectStateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.review.dto.ShopReviewDTO;
import com.shoppingcenter.app.controller.review.dto.ShopReviewEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.SortQuery.Direction;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.usecase.GetAllShopReviewUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopReviewByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.WriteShopReviewUseCase;

@Facade
public class ShopReviewFacadeImpl implements ShopReviewFacade {

    @Autowired
    private WriteShopReviewUseCase writeShopReviewUseCase;

    @Autowired
    private GetShopReviewByUserUseCase getShopReviewByUserUseCase;

    @Autowired
    private GetAllShopReviewUseCase getAllShopReviewUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Retryable(value = { StaleObjectStateException.class })
    @Transactional
    @Override
    public void writeReview(ShopReviewEditDTO review) {
        writeShopReviewUseCase.apply(modelMapper.map(review, ShopReview.class));
    }

    @Retryable(value = { StaleObjectStateException.class })
    @Transactional
    @Override
    public void updateReview(ShopReviewEditDTO review) {
        writeShopReviewUseCase.apply(modelMapper.map(review, ShopReview.class));
    }

    @Override
    public void delete(ShopReviewDTO review) {

    }

    @Override
    public ShopReviewDTO findUserReview(long shopId, long userId) {
        var source = getShopReviewByUserUseCase.apply(shopId, userId);
        return source != null ? modelMapper.map(source, ShopReviewDTO.class) : null;
    }

    @Override
    public PageData<ShopReviewDTO> findReviewsByShop(long shopId, Direction direction, Integer page) {
        return modelMapper.map(getAllShopReviewUseCase.apply(shopId, page, SortQuery.of(direction, "modifiedAt")),
                ShopReviewDTO.pagType());
    }

}
