package com.marketplace.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.shop.ShopRating;
import com.marketplace.domain.shop.dao.ShopRatingDao;

@Repository
public class ShopRatingDaoImpl implements ShopRatingDao {

	@Autowired
	private ShopRatingRepo shopRatingRepo;

	@Autowired
	private ShopRepo shopRepo;

	@Override
	public void save(ShopRating values) {
		var entity = shopRatingRepo.findById(values.getShopId()).orElseGet(ShopRatingEntity::new);
		entity.setRating(values.getRating());
		entity.setCount(values.getCount());
		entity.setShop(shopRepo.getReferenceById(values.getShopId()));

		shopRatingRepo.save(entity);
	}

	@Override
	public void updateRatingAndCount(long shopId) {
		shopRatingRepo.updateRatingAndCount(shopId);
	}

	@Override
	public void updateRating(long shopId) {
		shopRatingRepo.updateRating(shopId);
	}

	@Override
	public ShopRating findByShop(long shopId) {
		return shopRatingRepo.findById(shopId).map(ShopRatingMapper::toDomain).orElse(null);
	}

}
