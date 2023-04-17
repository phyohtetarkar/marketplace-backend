package com.shoppingcenter.domain.banner.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;

public class GetBannerByIdUseCase {

	private BannerDao dao;

	public GetBannerByIdUseCase(BannerDao dao) {
		this.dao = dao;
	}

	public Banner apply(int id) {
		Banner banner = dao.findById(id);

		if (banner == null) {
			throw new ApplicationException("Banner not found");
		}

		return banner;
	}

}
