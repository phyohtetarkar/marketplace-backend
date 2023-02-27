package com.shoppingcenter.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.PageQueryMapper;
import com.shoppingcenter.data.SortQueryMapper;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;

@Repository
public class ShopReviewDaoImpl implements ShopReviewDao {

    @Autowired
    private ShopReviewRepo shopReviewRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private UserRepo userRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public void save(ShopReview review) {
        var entity = shopReviewRepo.findById(review.getId()).orElseGet(ShopReviewEntity::new);
        entity.setRating(review.getRating());
        entity.setDescription(review.getDescription());
        if (entity.getId() == 0) {
            entity.setShop(shopRepo.getReferenceById(review.getShopId()));
            entity.setUser(userRepo.getReferenceById(review.getUserId()));
        }

        shopReviewRepo.save(entity);
    }

    @Override
    public void delete(String userId, long id) {
        shopReviewRepo.deleteByIdAndUser_Id(id, userId);
    }

    @Override
    public boolean existsByUserAndShop(String userId, long shopId) {
        return shopReviewRepo.existsByShop_IdAndUser_Id(shopId, userId);
    }

    @Override
    public double averageRatingByShop(long shopId) {
        return shopReviewRepo.averageRatingByShop(shopId);
    }

    @Override
    public ShopReview findUserReview(long shopId, String userId) {
        return shopReviewRepo.findByShop_IdAndUser_Id(shopId, userId)
                .map(e -> ShopReviewMapper.toDomain(e, imageUrl)).orElse(null);
    }

    @Override
    public PageData<ShopReview> findReviewByShop(long shopId, PageQuery paging, SortQuery sort) {
        var sortBy = SortQueryMapper.fromQuery(sort);
        var request = PageQueryMapper.fromPageQuery(paging, sortBy);

        var pageResult = shopReviewRepo.findByShop_Id(shopId, request);

        return PageDataMapper.map(pageResult, e -> ShopReviewMapper.toDomain(e, imageUrl));
    }

}
