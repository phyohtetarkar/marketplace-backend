package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class UploadShopLogoUseCase {

	private ShopDao dao;

	private FileStorageAdapter fileStorageAdapter;

	public void apply(long shopId, UploadFile file) {

		if (file == null || file.isEmpty()) {
			throw new ApplicationException("Logo image must not empty");
		}

		if (!dao.existsById(shopId)) {
			throw new ApplicationException("Shop not found");
		}

		var oldLogo = dao.getLogo(shopId);

		String suffix = file.getExtension();
		String imageName = String.format("%d_logo.%s", shopId, suffix);

		dao.updateLogo(shopId, imageName);

		var dir = Constants.IMG_SHOP_ROOT;

		fileStorageAdapter.write(file, dir, imageName);

		if (Utils.hasText(oldLogo) && !oldLogo.equals(imageName)) {
			fileStorageAdapter.delete(dir, oldLogo);
		}
	}

}
