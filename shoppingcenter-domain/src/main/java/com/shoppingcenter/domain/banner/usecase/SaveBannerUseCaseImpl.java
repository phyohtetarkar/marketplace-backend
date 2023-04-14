package com.shoppingcenter.domain.banner.usecase;

import java.util.function.BiConsumer;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;

public class SaveBannerUseCaseImpl implements SaveBannerUseCase {

    private BannerDao dao;

    private FileStorageAdapter fileStorageAdapter;

    public SaveBannerUseCaseImpl(BannerDao dao, FileStorageAdapter fileStorageAdapter) {
        this.dao = dao;
        this.fileStorageAdapter = fileStorageAdapter;
    }

    @Override
    public void apply(Banner banner) {
        if (banner.getId() <= 0 && (banner.getFile() == null || banner.getFile().getSize() <= 0)) {
            throw new ApplicationException("Required banner image");
        }

        BiConsumer<String, String> pendingUpload = null;

        String oldImage = banner.getImage();

        if (banner.getFile() != null) {
            var timestamp = System.currentTimeMillis();
            var suffix = banner.getFile().getExtension();
            var imageName = String.format("%d_%s.%s",
                    timestamp,
                    Utils.generateRandomCode(8),
                    suffix);

            banner.setImage(imageName);

            pendingUpload = (old, newImage) -> {
                String dir = Constants.IMG_BANNER_ROOT;
                fileStorageAdapter.write(banner.getFile(), dir, newImage);

                if (Utils.hasText(old)) {
                    fileStorageAdapter.delete(dir, old);
                }
            };
        }

        dao.save(banner);

        if (pendingUpload != null) {
            pendingUpload.accept(oldImage, banner.getImage());
        }

    }

}
