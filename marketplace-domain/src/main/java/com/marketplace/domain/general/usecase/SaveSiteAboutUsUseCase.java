package com.marketplace.domain.general.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.HTMLStringSanitizer;
import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.general.dao.SiteSettingDao;

@Component
public class SaveSiteAboutUsUseCase {

	@Autowired
	private SiteSettingDao dao;
	
	@Autowired
	private HTMLStringSanitizer htmlStringSanitizer;
	
	@Transactional
	public void apply(String value) {
		if (!Utils.hasText(value)) {
			throw new ApplicationException("About us must not empty");
		}
		
		dao.updateAboutUs(SiteSetting.DEFAULT_ID, htmlStringSanitizer.sanitize(value));
		
	}
	
}
