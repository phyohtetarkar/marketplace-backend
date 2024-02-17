package com.marketplace.domain.banner.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.Utils;
import com.marketplace.domain.banner.BannerDao;
import com.marketplace.domain.common.FileStorageAdapter;

@Component
public class DeleteBannerUseCase {

	@Autowired
    private BannerDao dao;

	@Autowired
    private FileStorageAdapter fileStorageAdapter;

	@Transactional
    public void apply(int id) {
        if (!dao.existsById(id)) {
            throw new ApplicationException("Banner not found");
        }
        var imageName = dao.getBannerImage(id);
        dao.delete(id);

        if (Utils.hasText(imageName)) {
            fileStorageAdapter.delete(Constants.IMG_BANNER_ROOT, imageName);
        }
    }

}
