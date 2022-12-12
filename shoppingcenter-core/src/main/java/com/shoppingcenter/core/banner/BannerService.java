package com.shoppingcenter.core.banner;

import java.util.List;

import com.shoppingcenter.core.banner.model.Banner;

public interface BannerService {

	void save(Banner banner);

	void delete(int id);

	Banner findById(int id);

	List<Banner> findAll();
}
