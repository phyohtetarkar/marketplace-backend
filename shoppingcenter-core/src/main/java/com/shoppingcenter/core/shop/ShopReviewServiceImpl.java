package com.shoppingcenter.core.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.Utils;
import com.shoppingcenter.core.shop.model.ShopReview;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.shop.ShopReviewEntity;
import com.shoppingcenter.data.shop.ShopReviewRepo;

@Service
public class ShopReviewServiceImpl implements ShopReviewService {

    @Autowired
    private ShopReviewRepo shopReviewRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Value("${app.image.base-url}")
    private String baseUrl;

    @Override
    public void writeReview(ShopReview review) {
        ShopReviewEntity.Id id = new ShopReviewEntity.Id();
        id.setUserId(review.getUserId());
        id.setShopId(review.getShopId());

        if (!shopRepo.existsById(review.getShopId())) {
            throw new ApplicationException();
        }

        if (shopReviewRepo.existsById(id)) {
            throw new ApplicationException();
        }

        ShopReviewEntity entity = new ShopReviewEntity();
        entity.setId(id);
        entity.setRating(review.getRating());
        entity.setDescription(review.getDescription());

        shopReviewRepo.save(entity);

        double averateRating = shopReviewRepo.averageRatingByShop(review.getShopId());
        shopRepo.updateRating(review.getShopId(), averateRating);
    }

    @Override
    public void deleteReview(long shopId, String userId) {
        ShopReviewEntity.Id id = new ShopReviewEntity.Id();
        id.setUserId(userId);
        id.setShopId(shopId);

        shopReviewRepo.deleteById(id);
    }

    @Override
    public PageData<ShopReview> findReviewsByShop(long shopId, Direction direction, Integer page) {
        Sort sort = Sort.by(direction, "createdAt");
        PageRequest request = PageRequest.of(Utils.normalizePage(page), Constants.PAGE_SIZE, sort);

        Page<ShopReviewEntity> pageResult = shopReviewRepo.findByShopId(shopId, request);

        PageData<ShopReview> data = new PageData<>();
        data.setContents(pageResult.map(e -> ShopReview.create(e, baseUrl)).toList());
        data.setCurrentPage(pageResult.getNumber());
        data.setTotalPage(pageResult.getTotalPages());
        data.setPageSize(pageResult.getNumberOfElements());
        data.setTotalElements(pageResult.getTotalElements());

        return data;
    }

}
