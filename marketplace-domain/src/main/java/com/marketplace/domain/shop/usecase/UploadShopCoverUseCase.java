package com.marketplace.domain.shop.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.UploadFile;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class UploadShopCoverUseCase {

	@Autowired
	private ShopDao dao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;

	@Transactional
	public void apply(long shopId, UploadFile file) {
		if (file == null || file.isEmpty()) {
			throw new ApplicationException("Cover image must not empty");
		}

		var fileSize = file.getSize() / (1024.0 * 1024.0);

		if (fileSize > 0.512) {
			throw new ApplicationException("File size must not greater than 512KB");
		}

		if (!dao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}
		
		var oldImage = dao.getCover(shopId);

		var suffix = file.getExtension();
		var dateTime = Utils.getCurrentDateTimeFormatted();
		var imageName = String.format("shop-cover-%d-%s.%s", shopId, dateTime, suffix);

		dao.updateCover(shopId, imageName);

		var dir = Constants.IMG_SHOP_ROOT;

		fileStorageAdapter.write(file, dir, imageName);
		
		if (Utils.hasText(oldImage)) {
			fileStorageAdapter.delete(dir, oldImage);
		}
	}

}
