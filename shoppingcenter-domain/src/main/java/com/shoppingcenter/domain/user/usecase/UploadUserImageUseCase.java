package com.shoppingcenter.domain.user.usecase;

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

        if (file == null || file.getSize() <= 0) {
            throw new ApplicationException("User image must not empty");
        }

        String oldImage = dao.getImage(userId);

        var timestamp = System.currentTimeMillis();
        String suffix = file.getExtension();
        String imageName = String.format("%d_%d.%s", userId, timestamp, suffix);

        dao.updateImage(userId, imageName);

        var dir = Constants.IMG_USER_ROOT;

        fileStorageAdapter.write(file, dir, imageName);

        if (Utils.hasText(oldImage) && !oldImage.equals(imageName)) {
            fileStorageAdapter.delete(dir, oldImage);
        }
    }

}
