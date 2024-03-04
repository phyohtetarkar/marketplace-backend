package com.marketplace.domain.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.UploadFile;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class UploadUserImageUseCase {

	@Autowired
	private UserDao dao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;

	@Transactional
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
		
		var old = dao.getImage(userId);

		var suffix = file.getExtension();
		var dateTime = Utils.getCurrentDateTimeFormatted();
		var imageName = String.format("profile-image-%d-%s.%s", userId, dateTime, suffix);

		dao.updateImage(userId, imageName);

		var dir = Constants.IMG_USER_ROOT;

		fileStorageAdapter.write(file, dir, imageName);
		
        if (Utils.hasText(old)) {
            fileStorageAdapter.delete(dir, old);
        }
		
	}

}
