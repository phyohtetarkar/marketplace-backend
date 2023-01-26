package com.shoppingcenter.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.shop.ShopReviewEntity;
import com.shoppingcenter.data.shop.ShopReviewRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.shop.model.ShopReview;

@Service
public class ShopReviewServiceImpl implements ShopReviewService {

    @Autowired
    private ShopReviewRepo shopReviewRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private UserRepo userRepo;

    @Value("${app.image.base-url}")
    private String baseUrl;

    @Override
    public void writeReview(ShopReview review) {

        if (!shopRepo.existsById(review.getShopId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Shop not found");
        }

        if (!StringUtils.hasText(review.getUserId()) || !userRepo.existsById(review.getUserId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "User not found");
        }

        if (shopReviewRepo.existsByShop_IdAndUser_Id(review.getShopId(), review.getUserId())) {
            throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, "Review already given");
        }

        ShopReviewEntity entity = new ShopReviewEntity();
        entity.setRating(review.getRating());
        entity.setDescription(review.getDescription());
        entity.setUser(userRepo.getReferenceById(review.getUserId()));
        entity.setShop(shopRepo.getReferenceById(review.getShopId()));

        shopReviewRepo.save(entity);

        double averateRating = shopReviewRepo.averageRatingByShop(review.getShopId());
        shopRepo.updateRating(review.getShopId(), averateRating);
    }

    @Override
    public void delete(long id) {
        shopReviewRepo.deleteById(id);
    }

    @Override
    public PageData<ShopReview> findReviewsByShop(long shopId, Direction direction, Integer page) {
        Sort sort = Sort.by(direction, "createdAt");
        PageRequest request = PageRequest.of(Utils.normalizePage(page), Constants.PAGE_SIZE, sort);

        Page<ShopReviewEntity> pageResult = shopReviewRepo.findByShop_Id(shopId, request);

        return PageData.build(pageResult, e -> ShopReview.create(e, baseUrl));
    }

}
