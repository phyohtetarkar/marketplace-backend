package com.marketplace.domain.product.usecase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.UploadFile;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductImage;
import com.marketplace.domain.product.dao.ProductDao;
import com.marketplace.domain.product.dao.ProductImageDao;

@Component
public class UpdateProductImagesUseCase {
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImageDao imageDao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;
	
	@Transactional
	public void apply(long shopId, long productId, List<ProductCreateInput.Image> values) {
		if (!productDao.existsByIdAndShop(productId, shopId)) {
			throw new ApplicationException("Product not found");
		}
		
		var images = Optional.ofNullable(values).orElseGet(ArrayList::new);

		boolean atLeastOneImage = images.stream().anyMatch(img -> img.isDeleted() == false);

		if (!atLeastOneImage) {
			throw new ApplicationException("At least one image required");
		}
		
		var deletedImages = new ArrayList<String>();
		var uploadedImages = new HashMap<String, UploadFile>();

		var deletedImageList = new ArrayList<Long>();
		
		var dateTime = LocalDateTime.now(ZoneOffset.UTC);
		var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		for (var image : images) {
			if (image.isDeleted()) {
				deletedImages.add(imageDao.getImageName(image.getId()));
				deletedImageList.add(image.getId());
				continue;
			}

			if (image.getId() <= 0 && (image.getFile() == null || image.getFile().getSize() <= 0)) {
				throw new ApplicationException("Uploaded image file must not empty");
			}

			if (image.getId() <= 0 && image.getFile() != null && image.getFile().getSize() > 0) {

				var fileSize = image.getFile().getSize() / (1024.0 * 1024.0);

				if (fileSize > 0.512) {
					throw new ApplicationException("File size must not greater than 512KB");
				}

				var suffix = image.getFile().getExtension();
				var dateTimeStr = dateTime.format(dateTimeFormatter);
				var imageName = String.format("product-image-%d-%s.%s", productId, dateTimeStr, suffix);

				image.setName(imageName);
				image.setSize(image.getFile().getSize());

				uploadedImages.put(imageName, image.getFile());
			}

			ProductImage pm = null;

			if (image.getId() <= 0) {
				pm = imageDao.create(productId, image);
			} else {
				pm = imageDao.update(image);
			}

			if (pm.isThumbnail()) {
				productDao.updateThumbnail(productId, pm.getName());
			}

			dateTime = dateTime.plus(1, ChronoUnit.SECONDS);
		}

		imageDao.deleteAll(deletedImageList);
		
		var dir = Constants.IMG_PRODUCT_ROOT;

		fileStorageAdapter.write(uploadedImages, dir);

		if (deletedImages.size() > 0) {
			fileStorageAdapter.delete(dir, deletedImages);
		}

	}
}
