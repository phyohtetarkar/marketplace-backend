package com.shoppingcenter.domain.banner.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;

public class DeleteBannerUseCase {

    private BannerDao dao;

    private FileStorageAdapter fileStorageAdapter;

    public DeleteBannerUseCase(BannerDao dao, FileStorageAdapter fileStorageAdapter) {
        this.dao = dao;
        this.fileStorageAdapter = fileStorageAdapter;
    }

    public void apply(int id) {
        if (!dao.existsById(id)) {
            throw new ApplicationException("Banner not found");
        }
        String imageName = dao.getBannerImage(id);
        dao.delete(id);

        if (Utils.hasText(imageName)) {
            fileStorageAdapter.delete(Constants.IMG_BANNER_ROOT, imageName);
        }
    }

}
