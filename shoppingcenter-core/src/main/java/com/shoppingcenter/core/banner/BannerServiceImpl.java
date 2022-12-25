package com.shoppingcenter.core.banner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.banner.model.Banner;
import com.shoppingcenter.data.banner.BannerEntity;
import com.shoppingcenter.data.banner.BannerRepo;

@Service
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerRepo repo;

	@Value("${app.image.base-url}")
	private String baseUrl;

	@Transactional
	@Override
	public void save(Banner banner) {
		try {
			BannerEntity entity = repo.findById(banner.getId()).orElseGet(BannerEntity::new);
			entity.setLink(banner.getLink());
			entity.setPosition(banner.getPosition());

			BannerEntity result = repo.save(entity);

			// TODO: upload image
		} catch (Exception e) {

		}

	}

	@Transactional
	@Override
	public void delete(int id) {
		BannerEntity entity = repo.findById(id).orElseThrow(() -> new ApplicationException("Banner not found."));

		repo.deleteById(id);

		// TODO delete image
	}

	@Override
	public Banner findById(int id) {
		return repo.findById(id).map(e -> Banner.create(e, baseUrl)).orElse(null);
	}

	@Override
	public List<Banner> findAll() {
		return repo.findAll().stream().map(e -> Banner.create(e, baseUrl)).collect(Collectors.toList());
	}

}
