package com.marketplace.domain.general.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.general.dao.SiteSettingDao;

@Component
public class GetSiteSettingUseCase {

	@Autowired
	private SiteSettingDao dao;
	
	@Transactional(readOnly = true)
	public SiteSetting apply() {
		return dao.getSiteSetting(SiteSetting.DEFAULT_ID);
	}
	
}
