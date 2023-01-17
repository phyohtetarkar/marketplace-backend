package com.shoppingcenter.core.banner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.banner.model.Banner;
import com.shoppingcenter.core.storage.FileStorageService;
import com.shoppingcenter.data.banner.BannerEntity;
import com.shoppingcenter.data.banner.BannerRepo;

@Service
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerRepo repo;

	@Autowired
	@Qualifier("local")
	private FileStorageService storageService;

	@Value("${app.image.base-url}")
	private String imageUrl;

	@Value("${app.image.base-path}")
	private String imagePath;

	@Transactional
	@Override
	public void save(Banner banner) {
		if (banner.getId() > 0 && banner.getFile().getSize() <= 0) {
			throw new ApplicationException("Banner image required");
		}
		try {
			BannerEntity entity = repo.findById(banner.getId()).orElseGet(BannerEntity::new);
			entity.setLink(banner.getLink());
			entity.setPosition(banner.getPosition());

			BannerEntity result = repo.save(entity);

			if (banner.getFile() != null) {
				String name = String.format("banner-%d", result.getId());
				String dir = imagePath + "/banner";
				String image = storageService.write(banner.getFile(), dir, name);
				result.setImage(image);
			}
		} catch (Exception e) {
			throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, e.getMessage());
		}

	}

	@Transactional
	@Override
	public void delete(int id) {
		if (!repo.existsById(id)) {
			throw new ApplicationException(ErrorCodes.NOT_FOUND);
		}
		BannerEntity entity = repo.getReferenceById(id);

		String image = entity.getImage();

		repo.deleteById(id);

		try {
			String dir = imagePath + "/banner";
			storageService.delete(dir, image);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Banner findById(int id) {
		return repo.findById(id).map(e -> Banner.create(e, imageUrl)).orElse(null);
	}

	@Override
	public List<Banner> findAll() {
		return repo.findAll(Sort.by("position")).stream()
				.map(e -> Banner.create(e, imageUrl))
				.collect(Collectors.toList());
	}

}
