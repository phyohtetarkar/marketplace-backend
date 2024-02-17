package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.HTMLStringSanitizer;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopUpdateInput;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class UpdateShopUseCase {

	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private HTMLStringSanitizer htmlStringSanitizer;

	public Shop apply(ShopUpdateInput values) {
		if (!shopDao.existsById(values.getShopId())) {
			throw new ApplicationException("Shop not found");
		}
		
		var rawSlug = Utils.convertToSlug(values.getSlug());

		if (!Utils.hasText(rawSlug)) {
			throw new ApplicationException("Invalid slug value");
		}
		
		var slug = Utils.generateSlug(rawSlug, v -> shopDao.existsByIdNotAndSlug(values.getShopId(), v));
    	values.setSlug(slug);

		values.setAbout(htmlStringSanitizer.sanitize(values.getAbout()));

		return shopDao.update(values);
	}

}
