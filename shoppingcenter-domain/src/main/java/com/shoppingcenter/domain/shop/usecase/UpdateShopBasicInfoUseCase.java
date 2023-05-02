package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopGeneral;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class UpdateShopBasicInfoUseCase {

	private ShopDao dao;

	private HTMLStringSanitizer htmlStringSanitizer;

	public Shop apply(ShopGeneral general) {
		if (!dao.existsById(general.getShopId())) {
			throw new ApplicationException("Shop not found");
		}

		var slug = Utils.convertToSlug(general.getName());

		if (!Utils.hasText(slug)) {
			throw new ApplicationException("Invalid slug value");
		}

		general.setSlug(slug);

		general.setAbout(htmlStringSanitizer.sanitize(general.getAbout()));

		dao.updateGeneralInfo(general);

		var shop = dao.findById(general.getShopId());

		return shop;
	}

}
