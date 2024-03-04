package com.marketplace.domain.banner.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.Utils;
import com.marketplace.domain.banner.Banner;
import com.marketplace.domain.banner.BannerDao;
import com.marketplace.domain.banner.BannerInput;
import com.marketplace.domain.common.FileStorageAdapter;

@Component
public class SaveBannerUseCase {

	@Autowired
    private BannerDao dao;

	@Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Transactional
    public Banner apply(BannerInput values) {
    	var file = values.getFile();
        if (values.getId() <= 0 && (file == null || file.isEmpty())) {
            throw new ApplicationException("Required banner image");
        }

        values.setLink(values.getLink());
        values.setPosition(values.getPosition());


        var result = dao.save(values);
        
        if (file != null && !file.isEmpty()) {
			var suffix = file.getExtension();
			var dateTime = Utils.getCurrentDateTimeFormatted();
			var imageName = String.format("banner-image-%d-%s.%s", result.getId(), dateTime, suffix);
			
			dao.updateImage(result.getId(), imageName);

            var dir = Constants.IMG_BANNER_ROOT;
            fileStorageAdapter.write(file, dir, imageName);
            
            var old = result.getImage();
            
            if (Utils.hasText(old)) {
                fileStorageAdapter.delete(dir, old);
            }
            result.setImage(imageName);
            
        }
        
        return result;

    }

}
