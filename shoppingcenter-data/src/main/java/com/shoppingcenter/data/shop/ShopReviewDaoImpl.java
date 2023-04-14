package com.shoppingcenter.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.PageQueryMapper;
import com.shoppingcenter.data.SortQueryMapper;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.SortQuery;
import com.shoppingcenter.domain.common.AppProperties;
import com.shoppingcenter.domain.shop.ShopReview;
import com.shoppingcenter.domain.shop.dao.ShopReviewDao;

@Repository
public class ShopReviewDaoImpl implements ShopReviewDao {

    @Autowired
    private ShopReviewRepo shopReviewRepo;

    // @Autowired
    // private ShopRepo shopRepo;

    // @Autowired
    // private UserRepo userRepo;

    @Autowired
    private AppProperties properties;

    @Override
    public void save(ShopReview review) {
        var id = new ShopReviewEntity.ID(review.getShopId(), review.getUserId());
        var entity = shopReviewRepo.findById(id).orElseGet(ShopReviewEntity::new);
        entity.setId(id);
        entity.setRating(review.getRating());
        entity.setDescription(review.getDescription());
        // entity.setShop(shopRepo.getReferenceById(review.getShopId()));
        // entity.setUser(userRepo.getReferenceById(review.getUserId()));

        shopReviewRepo.save(entity);
    }

    @Override
    public void delete(long shopId, long userId) {
        var id = new ShopReviewEntity.ID(shopId, userId);
        shopReviewRepo.deleteById(id);
    }

    @Override
    public boolean exists(long shopId, long userId) {
        var id = new ShopReviewEntity.ID(shopId, userId);
        return shopReviewRepo.existsById(id);
    }

    @Override
    public double averageRatingByShop(long shopId) {
        return shopReviewRepo.averageRatingByShop(shopId);
    }

    @Override
    public ShopReview findUserReview(long shopId, long userId) {
        var id = new ShopReviewEntity.ID(shopId, userId);
        return shopReviewRepo.findById(id)
                .map(e -> ShopReviewMapper.toDomain(e, properties.getImageUrl())).orElse(null);
    }

    @Override
    public PageData<ShopReview> findReviewByShop(long shopId, PageQuery paging, SortQuery sort) {
        var sortBy = SortQueryMapper.fromQuery(sort);
        var request = PageQueryMapper.fromPageQuery(paging, sortBy);

        var pageResult = shopReviewRepo.findByShopId(shopId, request);

        return PageDataMapper.map(pageResult, e -> ShopReviewMapper.toDomain(e, properties.getImageUrl()));
    }

}
