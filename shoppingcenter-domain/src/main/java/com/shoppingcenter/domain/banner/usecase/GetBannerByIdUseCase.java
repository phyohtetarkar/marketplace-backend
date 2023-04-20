package com.shoppingcenter.domain.banner.usecase;

import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;

public class GetBannerByIdUseCase {

	private BannerDao dao;

	public GetBannerByIdUseCase(BannerDao dao) {
		this.dao = dao;
	}

	public Banner apply(int id) {
		var banner = dao.findById(id);
		return banner;
	}

}
