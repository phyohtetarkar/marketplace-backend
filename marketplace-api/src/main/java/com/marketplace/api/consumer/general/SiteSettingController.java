package com.marketplace.api.consumer.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.general.dao.SiteSettingDao;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/site-setting")
@Tag(name = "Consumer")
public class SiteSettingController {
	
	@Autowired
	private SiteSettingDao siteSettingDao;
	
	@Autowired
	private ConsumerDataMapper mapper;
	
	@GetMapping("assets")
	public SiteAssetsDTO getAssets() {
		var source = siteSettingDao.getSiteAssets(SiteSetting.DEFAULT_ID);
		return mapper.map(source);
	}
	
	@GetMapping("about-us")
	public String getAboutUs() {
		return siteSettingDao.getAboutUs(SiteSetting.DEFAULT_ID);
	}
	
	@GetMapping("terms-and-conditions")
	public String getTermsAndConditions() {
		return siteSettingDao.getTermsAndConditions(SiteSetting.DEFAULT_ID);
	}
	
	@GetMapping("privacy-policy")
	public String getPrivacyPolicy() {
		return siteSettingDao.getPrivacyPolicy(SiteSetting.DEFAULT_ID);
	}
	
}
