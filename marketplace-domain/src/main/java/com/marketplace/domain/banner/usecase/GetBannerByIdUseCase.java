package com.marketplace.domain.banner.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.banner.Banner;
import com.marketplace.domain.banner.BannerDao;

@Component
public class GetBannerByIdUseCase {

	@Autowired
	private BannerDao dao;

	public Banner apply(int id) {
		var banner = dao.findById(id);
		if (banner == null) {
			throw ApplicationException.notFound("Banner not found");
		}
		return banner;
	}

}
