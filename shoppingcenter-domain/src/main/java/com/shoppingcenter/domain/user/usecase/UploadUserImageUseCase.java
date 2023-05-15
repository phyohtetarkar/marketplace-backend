package com.shoppingcenter.domain.user.usecase;

import java.io.File;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.user.UserDao;

public class UploadUserImageUseCase {

	private UserDao dao;

	private FileStorageAdapter fileStorageAdapter;

	public UploadUserImageUseCase(UserDao dao, FileStorageAdapter fileStorageAdapter) {
		this.dao = dao;
		this.fileStorageAdapter = fileStorageAdapter;
	}

	public void apply(long userId, UploadFile file) {
		if (!dao.existsById(userId)) {
			throw new ApplicationException("User not found");
		}

		if (file == null || file.isEmpty()) {
			throw new ApplicationException("User image must not empty");
		}

		var fileSize = file.getSize() / (1024.0 * 1024.0);

		if (fileSize > 0.512) {
			throw new ApplicationException("File size must not greater than 512KB");
		}

		var oldImage = dao.getImage(userId);

		var suffix = file.getExtension();
		var imageName = String.format("profile.%s", suffix);

		dao.updateImage(userId, imageName);

		var dir = Constants.IMG_USER_ROOT + File.separator + userId;

		fileStorageAdapter.write(file, dir, imageName);

		if (Utils.hasText(oldImage) && !oldImage.equals(imageName)) {
			fileStorageAdapter.delete(dir, oldImage);
		}
	}

}
