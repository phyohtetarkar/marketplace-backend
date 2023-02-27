package com.shoppingcenter.domain.banner.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;

public class DeleteBannerUseCaseImpl implements DeleteBannerUseCase {

    private BannerDao dao;

    private FileStorageAdapter fileStorageAdapter;

    public DeleteBannerUseCaseImpl(BannerDao dao, FileStorageAdapter fileStorageAdapter) {
        this.dao = dao;
        this.fileStorageAdapter = fileStorageAdapter;
    }

    @Override
    public void apply(int id) {
        if (!dao.existsById(id)) {
            throw new ApplicationException("Banner not found");
        }
        Banner banner = dao.findById(id);
        String imageName = banner.getImage();
        dao.delete(id);

        if (Utils.hasText(imageName)) {
            fileStorageAdapter.delete("banner", imageName);
        }
    }

}
