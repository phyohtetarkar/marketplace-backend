package com.shoppingcenter.domain.user.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.user.UserDao;

public class UploadUserImageUseCaseImpl implements UploadUserImageUseCase {

    private UserDao dao;

    private FileStorageAdapter fileStorageAdapter;

    public UploadUserImageUseCaseImpl(UserDao dao, FileStorageAdapter fileStorageAdapter) {
        this.dao = dao;
        this.fileStorageAdapter = fileStorageAdapter;
    }

    @Override
    public void apply(String userId, UploadFile file) {
        if (!dao.existsById(userId)) {
            throw new ApplicationException("User not found");
        }

        if (file == null || file.getSize() <= 0) {
            throw new ApplicationException("User image must not empty");
        }

        String oldImage = dao.getImage(userId);

        String suffix = file.getExtension();
        String imageName = String.format("%s.%s", userId, suffix);

        dao.updateImage(userId, imageName);

        fileStorageAdapter.write(file, "user", imageName);

        if (Utils.hasText(oldImage) && !oldImage.equals(imageName)) {
            fileStorageAdapter.delete("user", oldImage);
        }
    }

}
