package com.shoppingcenter.domain.banner.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.BannerDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;

public class SaveBannerUseCase {

    private BannerDao dao;

    private FileStorageAdapter fileStorageAdapter;

    public SaveBannerUseCase(BannerDao dao, FileStorageAdapter fileStorageAdapter) {
        this.dao = dao;
        this.fileStorageAdapter = fileStorageAdapter;
    }

    public void apply(Banner banner, UploadFile file) {
        if (banner.getId() <= 0 && (file == null || file.isEmpty())) {
            throw new ApplicationException("Required banner image");
        }

        banner.setLink(banner.getLink());
        banner.setPosition(banner.getPosition());


        var result = dao.save(banner);
        
        if (file != null) {
			String suffix = file.getExtension();
			String imageName = String.format("banner-%d.%s", result.getId(), suffix);

            banner.setImage(imageName);
            
            var dir = Constants.IMG_BANNER_ROOT;
            fileStorageAdapter.write(file, dir, imageName);
            
            var old = result.getImage();
            
            if (Utils.hasText(old) && !old.equals(imageName)) {
                fileStorageAdapter.delete(dir, old);
            }
            
            dao.updateImage(result.getId(), imageName);
        }

    }

}
