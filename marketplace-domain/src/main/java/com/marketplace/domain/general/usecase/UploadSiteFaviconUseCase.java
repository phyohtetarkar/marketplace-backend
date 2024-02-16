package com.marketplace.domain.general.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.UploadFile;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.general.dao.SiteSettingDao;

@Component
public class UploadSiteFaviconUseCase {

	@Autowired
	private SiteSettingDao dao;
	
	@Autowired
	private FileStorageAdapter fileStorageAdapter;
	
	@Transactional
	public void apply(UploadFile file) {
		if (file == null || file.isEmpty()) {
			throw new ApplicationException("Upload file must not empty");
		}
		
		var suffix = file.getExtension();
		
		var imageName = String.format("favicon.%s", suffix);
		
		dao.updateFavicon(SiteSetting.DEFAULT_ID, imageName);
		
		fileStorageAdapter.write(file, Constants.IMG_SITE_SETTING_ROOT, imageName);
	}
	
}
