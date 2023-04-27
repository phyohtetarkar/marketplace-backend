package com.shoppingcenter.domain.shop.usecase;

import java.io.File;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class UploadShopCoverUseCase {

	private ShopDao dao;

	private FileStorageAdapter fileStorageAdapter;

	public void apply(long shopId, UploadFile file) {
		if (file == null || file.isEmpty()) {
			throw new ApplicationException("Cover image must not empty");
		}

		if (!dao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}

		var oldCover = dao.getCover(shopId);

		String suffix = file.getExtension();
		String imageName = String.format("cover.%s", suffix);

		dao.updateCover(shopId, imageName);

		var dir = Constants.IMG_SHOP_ROOT + File.separator + shopId;

		fileStorageAdapter.write(file, dir, imageName);

		if (Utils.hasText(oldCover) && !oldCover.equals(imageName)) {
			fileStorageAdapter.delete(dir, oldCover);
		}
	}

}
