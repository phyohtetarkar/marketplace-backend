package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class UploadShopCoverUseCaseImpl implements UploadShopCoverUseCase {

    private ShopDao dao;

    private FileStorageAdapter fileStorageAdapter;

    @Override
    public void apply(long shopId, UploadFile file) {
        if (file == null || file.getSize() <= 0) {
            throw new ApplicationException("Cover image must not empty");
        }

        if (!dao.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }

        String oldCover = dao.getCover(shopId);

        var timestamp = System.currentTimeMillis();
        String suffix = file.getExtension();
        String imageName = String.format("cover_%d_%d.%s",
                shopId,
                timestamp,
                suffix);

        dao.updateCover(shopId, imageName);

        var dir = Constants.IMG_SHOP_ROOT;

        fileStorageAdapter.write(file, dir, imageName);

        if (Utils.hasText(oldCover)) {
            fileStorageAdapter.delete(dir, oldCover);
        }
    }

}
