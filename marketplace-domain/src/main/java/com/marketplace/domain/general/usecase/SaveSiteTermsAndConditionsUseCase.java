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
public class SaveSiteTermsAndConditionsUseCase {

	@Autowired
	private SiteSettingDao dao;
	
	@Autowired
	private HTMLStringSanitizer htmlStringSanitizer;
	
	@Transactional
	public void apply(String value) {
		if (!Utils.hasText(value)) {
			throw new ApplicationException("T&C must not empty");
		}
		
		dao.updateTermsAndConditions(SiteSetting.DEFAULT_ID, htmlStringSanitizer.sanitize(value));
		
	}
	
}
