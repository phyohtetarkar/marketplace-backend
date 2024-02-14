package com.marketplace.data.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.shop.ShopRepo;
import com.marketplace.data.user.UserRepo;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.review.ShopReview;
import com.marketplace.domain.review.ShopReviewDao;
import com.marketplace.domain.review.ShopReviewInput;

@Repository
public class ShopReviewDaoImpl implements ShopReviewDao {

    @Autowired
    private ShopReviewRepo shopReviewRepo;
    
    @Autowired
    private ShopRepo shopRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Override
    public void save(ShopReviewInput values) {
        var id = new ShopReviewEntity.ID(values.getShopId(), values.getUserId());
        var entity = shopReviewRepo.findById(id).orElseGet(ShopReviewEntity::new);
        entity.setRating(values.getRating());
        entity.setDescription(values.getDescription());
        entity.setUser(userRepo.getReferenceById(values.getUserId()));
        entity.setShop(shopRepo.getReferenceById(values.getShopId()));

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
    public ShopReview findUserReview(long shopId, long userId) {
        var id = new ShopReviewEntity.ID(shopId, userId);
        return shopReviewRepo.findById(id)
                .map(e -> ShopReviewMapper.toDomain(e)).orElse(null);
    }

    @Override
    public PageData<ShopReview> findReviewByShop(long shopId, PageQuery pageQuery) {
        var request = PageQueryMapper.fromPageQuery(pageQuery);

        var pageResult = shopReviewRepo.findByShopId(shopId, request);

        return PageDataMapper.map(pageResult, e -> ShopReviewMapper.toDomain(e));
    }

}
