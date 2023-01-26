package com.shoppingcenter.service.banner;

import java.util.List;

import com.shoppingcenter.service.banner.model.Banner;

public interface BannerService {

	void save(Banner banner);

	void delete(int id);

	Banner findById(int id);

	List<Banner> findAll();
}
