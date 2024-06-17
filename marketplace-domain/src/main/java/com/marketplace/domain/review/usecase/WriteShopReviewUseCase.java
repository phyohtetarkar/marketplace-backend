package com.marketplace.domain.review.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.review.ShopReviewDao;
import com.marketplace.domain.review.ShopReviewInput;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shop.dao.ShopRatingDao;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class WriteShopReviewUseCase {

	@Autowired
	private ShopReviewDao shopReviewDao;

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ShopRatingDao shopRatingDao;

	@Transactional
	public void apply(ShopReviewInput values) {
		if (!shopDao.existsById(values.getShopId())) {
			throw new ApplicationException("Shop not found");
		}

		if (!userDao.existsById(values.getUserId())) {
			throw new ApplicationException("User not found");
		}

		if (values.getRating().doubleValue() < 1 || values.getRating().doubleValue() > 5) {
			throw new ApplicationException("Rating must between 1 and 5");
		}

		if (!Utils.hasText(values.getDescription())) {
			throw new ApplicationException("Required description");
		}

		var exists = shopReviewDao.exists(values.getShopId(), values.getUserId());

		shopReviewDao.save(values);

		if (exists) {
			shopRatingDao.updateRating(values.getShopId());
		} else {
			shopRatingDao.updateRatingAndCount(values.getShopId());
		}
	}

}
