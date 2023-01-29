package com.shoppingcenter.service.banner;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.data.banner.BannerEntity;
import com.shoppingcenter.data.banner.BannerRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.banner.model.Banner;
import com.shoppingcenter.service.storage.FileStorageService;

@Service
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerRepo repo;

	@Autowired
	private FileStorageService storageService;

	@Value("${app.image.base-url}")
	private String imageUrl;

	@Value("${app.image.base-path}")
	private String imagePath;

	@Transactional
	@Override
	public void save(Banner banner) {
		try {
			BannerEntity entity = repo.findById(banner.getId()).orElseGet(BannerEntity::new);

			if (entity.getId() <= 0 && (banner.getFile() == null || banner.getFile().getSize() <= 0)) {
				throw new RuntimeException("Banner image required");
			}

			entity.setLink(banner.getLink());
			entity.setPosition(banner.getPosition());

			BannerEntity result = repo.save(entity);

			if (banner.getFile() != null) {
				// long millis = System.currentTimeMillis();
				String name = String.format("banner-%d.%s", result.getId(), banner.getFile().getExtension());
				String dir = imagePath + File.separator + "banner";
				// String oldImage = result.getImage();

				String image = storageService.write(banner.getFile(), dir, name);
				result.setImage(image);

				// if (StringUtils.hasText(oldImage)) {
				// storageService.delete(dir, oldImage);
				// }
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

		String dir = imagePath + File.separator + "banner";
		storageService.delete(dir, image);
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
