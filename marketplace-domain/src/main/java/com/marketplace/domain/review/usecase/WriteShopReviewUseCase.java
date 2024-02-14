package com.marketplace.domain.review.usecase;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
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

	@Retryable(noRetryFor = { ApplicationException.class })
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

        shopReviewDao.save(values);
        
        var shopRating = shopRatingDao.findByShop(values.getShopId());
        var rating = 0.0;
		var reviewCount = 0;
        
        if (shopRating != null) {
			rating = shopRating.getRating().doubleValue();
			reviewCount = shopRating.getCount();
		}
		
		var existingSumOfRating = rating * reviewCount;
		var newSumOfRating = existingSumOfRating + values.getRating().doubleValue();
		var newNumberOfRating = reviewCount + 1;
		var newAverageRating = newSumOfRating / newNumberOfRating;
		
		shopRating.setRating(BigDecimal.valueOf(newAverageRating));
		shopRating.setCount(newNumberOfRating);
		
		shopRatingDao.save(shopRating);
    }

}
